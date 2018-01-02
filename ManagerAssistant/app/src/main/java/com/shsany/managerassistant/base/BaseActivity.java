package com.shsany.managerassistant.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.shsany.managerassistant.api.Api;
import com.shsany.managerassistant.api.ApiWrapper;
import com.shsany.managerassistant.api.SimpleMyCallBack;
import com.shsany.managerassistant.common.ActivityPageManager;
import com.shsany.managerassistant.reponse.HttpExceptionBean;
import com.shsany.managerassistant.utils.ToastUtils;
import com.shsany.managerassistant.widget.dialog.DialogLoading;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by PC on 2017/11/8.
 * Activity 基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements View.OnClickListener{
    protected AppCompatActivity mContext;
    //使用CompositeSubscription来持有所有的Subscriptions
    protected CompositeSubscription mCompositeSubscription;
    //加载对话框
    protected DialogLoading loading;
    //来自哪个页面
    protected String fromWhere;
    //页面布局的根view
    protected View mContentView;
    //Api类的包装 对象
    protected ApiWrapper mApiwrapper;
    public T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不能横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mContext = this;
        //Activity管理
        ActivityPageManager.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID,null);
        setContentView(view);
    }

    public void setmContentView(View mContentView) {
        super.setContentView(mContentView);
        this.mContentView = mContentView;
        init();
    }

    /**
     * 初始化页面
     */
    public void init() {
        initFromWhere();
        initView();
        bindEvent();
    }

    protected void initFromWhere() {
        if (null != getIntent().getExtras()){
            if (getIntent().getExtras().containsKey("fromWhere")){
                fromWhere = getIntent().getExtras().getString("fromWhere").toString();
            }
        }
    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 绑定事件
     */
    public abstract void bindEvent();

    /**
     * 初始化api 根据需要 来初始化
     */
    public void initApi(){
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositeSubscription = new CompositeSubscription();
        //构建ApiWrapper对象
        mApiwrapper = new ApiWrapper();
    }

    public ApiWrapper getApiWrapper(){
        if (mApiwrapper == null){
            mApiwrapper = new ApiWrapper();
        }
        return mApiwrapper;
    }

    public CompositeSubscription getCompositeSubscription(){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    /**
     * 创建相应的presenter
     */
    public void createPresenter(T presenter){
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    public String getFromWhere(){
        return fromWhere;
    }

    protected <T>Subscriber newMySubscriber(final SimpleMyCallBack onNext){
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                hideLoadingDialog();
                onNext.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof Api.APIException){
                    Api.APIException exception = (Api.APIException) e;
                    ToastUtils.showShort(exception.message);
                }else if (e instanceof HttpException){
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    try{
                        String json = body.string();
                        Gson gson = new Gson();
                        HttpExceptionBean mHttpExceptionBean = gson.fromJson(json,HttpExceptionBean.class);
                        if (mHttpExceptionBean != null && mHttpExceptionBean.getMessage() != null){
                            ToastUtils.showShort(mHttpExceptionBean.getMessage());
                            onNext.onError(mHttpExceptionBean);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                hideLoadingDialog();
            }

            @Override
            public void onNext(T t) {
                if (!mCompositeSubscription.isUnsubscribed()){
                    onNext.onNext(t);
                }
            }
        };
    }

    /**
     * 将Fragment 添加到activity
     * @param fragment
     * @param frameId
     */
    protected void addFragmentToActivity(@NonNull Fragment fragment, int frameId){
        checkNotNull(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(frameId,fragment);
        transaction.commit();
    }

    /**
     * 显示一个Toast信息
     */
    public void showToast(String content){
        if (content != null){
            ToastUtils.showShort(content);
        }
    }

    public void showLoadingDialog(){
        if (loading == null){
            loading = new DialogLoading(this);
        }
        loading.show();
    }

    public void hideLoadingDialog(){
        if (loading != null){
            loading.dismiss();
        }
    }

    /**
     * 跳转页面
     * @param clazz
     */
    public void skipAct(Class clazz){
        Intent intent = new Intent(this,clazz);
        intent.putExtra("fromWhere",getClass().getSimpleName());
        startActivity(intent);
    }

    public void skipAct(Class clazz, Bundle bundle){
        Intent intent = new Intent(this,clazz);
        intent.putExtras(bundle);
        intent.putExtra("fromWhere",getClass().getSimpleName());
        startActivity(intent);
    }

    public void skipAct(Class clazz, Bundle bundle ,int flags){
        Intent intent = new Intent(this,clazz);
        intent.putExtra("fromWhere",getClass().getSimpleName());
        intent.setFlags(flags);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity释放子资源
        ActivityPageManager.unbindReferences(mContentView);
        ActivityPageManager.getInstance().removeActivity(this);
        mContentView = null;
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
        //解绑presenter
        if (presenter != null){
            presenter.unsubscribe();
        }
    }

    /*//管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();

    public static BaseActivity mActivity;

    //以下变量用于从左边滑动到邮编关闭的变量（类似ios自带的关闭效果）
//    private  int startX,startY,endX,endY,deltaX,deltaY;

    private VelocityTracker mVelocityTracker;
    private View mDecorView;
    private boolean isClose = true;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        mVelocityTracker =mVelocityTracker.obtain();
        mDecorView = getWindow().getDecorView();
        synchronized (mActivities){
            mActivities.add(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities){
            mActivities.remove(this);
        }
    }

    public void killAll(){
        //复制一份mActivities 集合A
        List<AppCompatActivity> copy;
        synchronized (mActivities){
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy){
            activity.finish();
        }
        //杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    *//**
     * 关闭activity时执行该动画
     * @param deltaX
     *//*
    public void closeAnimator(int deltaX){
        if (isClose){
            ValueAnimator animator = ValueAnimator.ofInt(deltaX,mDecorView.getWidth());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    mDecorView.scrollTo(-value,0);
                }
            });
            animator.setDuration(300);
            animator.start();
        }else {
            ValueAnimator animator = ValueAnimator.ofInt(deltaX,0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    mDecorView.scrollTo(-value, 0);
                }
            });
            animator.setDuration(300);
            animator.start();
        }
    }

    *//**
     * 颜色变化过度
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     *//*
//    public Object evaluateColor(float fraction, Object startValue, Object endValue){
//
//    }*/

}
