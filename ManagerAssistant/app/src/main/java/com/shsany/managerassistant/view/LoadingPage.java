package com.shsany.managerassistant.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shsany.managerassistant.R;

/**
 * Created by PC on 2017/11/13.
 */

public abstract class LoadingPage extends FrameLayout {

    private View loadingView;//正在加载中的界面
    private View errorView;//错误界面
    private View emptyView; //空界面

    public View contentView; //加载成功的界面

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;

    public int state = STATE_UNKNOWN;


    private Context mContext;
    private ImageView img;
    private AnimationDrawable mAnimationDrawable;

    public LoadingPage(@NonNull Context context) {
        this(context,null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();//初始化这种情况界面
    }

    private void init() {
        this.setBackgroundColor(getResources().getColor(R.color.color1));
        //把loadingView 添加到fragment上
        if (loadingView == null){
            loadingView = createLoadingView();
            this.addView(loadingView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        //把emptyView添加到fragment上
        if (emptyView == null){
            emptyView = createEmptyView();
            this.addView(emptyView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        //把errorView添加到fragment上
        if (errorView == null){
            errorView = createErrorView();
            this.addView(errorView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        showPage();//根据状态显示界面
    }

    private View createErrorView() {
        errorView = LayoutInflater.from(mContext).inflate(R.layout.basefragment_state_error,null);
        errorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                state = STATE_LOADING;
                showPage();
                loadData();
            }
        });
        return errorView;
    }

    private View createEmptyView() {
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.basefragment_state_empty,null);
        return emptyView;
    }

    private View createLoadingView() {
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.basefragment_state_loading,null);
        img = (ImageView)loadingView.getRootView().findViewById(R.id.img_progress);
        //加载动画 这边也可以直接用progressbar 可以看看topnews也下载刷新就是只用progressbar控制动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        //默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()){
            mAnimationDrawable.start();
        }
        return loadingView;
    }

    private void showPage() {
        if (loadingView != null){
            if (state == STATE_UNKNOWN || state == STATE_LOADING){
                loadingView.setVisibility(VISIBLE);
                //开始动画
                startAnimation();
            }else {
                //关闭动画
                stopAnimation();
                loadingView.setVisibility(GONE);
            }
        }
        if (state == STATE_EMPTY || state == STATE_ERROR || state == STATE_SUCCESS){
            //关闭动画
            stopAnimation();
        }
        if (emptyView != null){
            emptyView.setVisibility(state == STATE_EMPTY ? VISIBLE : GONE);
        }
        if (errorView != null){
            errorView.setVisibility(state == STATE_ERROR ? VISIBLE:GONE);
        }
        if (state == STATE_SUCCESS){
            if (contentView == null){
                contentView = LayoutInflater.from(mContext).inflate(getLayoutId(),null);
                addView(contentView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
                initView();
            }
            contentView.setVisibility(VISIBLE);
        }else {
            if (contentView != null){
                contentView.setVisibility(GONE);
            }
        }
    }

    /**
     * 自雷关于view的操邹（如setAdapter）都必须在这里面，否则会因为页面状态为不成功，而binding还没创建就引用而导致空指针。
     */
    protected abstract void initView();



    /**
     * 根据网络获取的数据返回的状态，每一个子类获取的网络的返回都不一样，所以要交给子类去完成
     */
    protected abstract void loadData();

    protected abstract int getLayoutId() ;

    private void startAnimation(){
        if (!mAnimationDrawable.isRunning()){
            mAnimationDrawable.start();
        }
    }

    private void stopAnimation(){
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()){
            mAnimationDrawable.stop();
        }
    }

}
