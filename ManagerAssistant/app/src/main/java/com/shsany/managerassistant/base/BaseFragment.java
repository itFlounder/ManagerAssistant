package com.shsany.managerassistant.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shsany.managerassistant.api.ApiWrapper;
import com.shsany.managerassistant.api.SimpleMyCallBack;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by PC on 2017/11/13.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements View.OnClickListener{
    public BaseActivity mContext;
    public View mContentView = null;
    //使用CompositeSubscription来持有所有的Subscriptions
    public CompositeSubscription mCompositeSubscription;
    //Api类的包装 对象
    public ApiWrapper mApiWrapper;

    public T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseActivity();
    }

    public BaseActivity getBaseActivity(){
        return (BaseActivity) this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null){
            mContentView = inflater.inflate(onSetLayoutId(),container,false);
            initView();
            bindEvent();
        }
        return mContentView;
    }

    protected abstract void initView();

    protected abstract void bindEvent();

    /**
     * 设置布局文件
     *
     * @return 返回布局文件资源Id
     */
    protected abstract int onSetLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑presenter
        if (presenter != null){
            presenter.unsubscribe();
        }
    }

    /**
     * 创建相应的presenter
     * @param presenter
     */
    public void createPresenter(T presenter){
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    /**
     * 初始化api， 根据需要初始化
     */
    public void initApi(){
        mCompositeSubscription = mContext.getCompositeSubscription();
        mApiWrapper = mContext.getApiWrapper();
    }

    public <T>Subscriber newMySubscriber(final SimpleMyCallBack onNext){
        return mContext.newMySubscriber(onNext);
    }

    public void showToast(String content){
        mContext.showToast(content);
    }

    public void showLoadingDialog(){
        mContext.showLoadingDialog();
    }

    public void hideLoadingDialog(){
        mContext.hideLoadingDialog();
    }

    public void skipAct(Class clazz){
        mContext.skipAct(clazz);
    }

    public void skipAct(Class clazz, Bundle bundle) {
        mContext.skipAct(clazz,bundle);
    }

    public void skipAct(Class clazz, Bundle bundle, int flags) {
        mContext.skipAct(clazz,bundle,flags);
    }


}
