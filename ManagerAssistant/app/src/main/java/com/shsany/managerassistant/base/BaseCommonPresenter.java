package com.shsany.managerassistant.base;

import com.google.gson.Gson;
import com.shsany.managerassistant.api.Api;
import com.shsany.managerassistant.api.ApiWrapper;
import com.shsany.managerassistant.api.SimpleMyCallBack;
import com.shsany.managerassistant.reponse.HttpExceptionBean;
import com.shsany.managerassistant.utils.ToastUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by PC on 2017/12/28.
 */

public class BaseCommonPresenter<T extends BaseView> {
    //Api类的包装 对象
    protected ApiWrapper mApiWrapper;
    //使用CompositeSubscription来持有所有的Subscriptions
    protected CompositeSubscription mCompositeSubscription;

    public T view;
    public BaseCommonPresenter(T view){
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositeSubscription = new CompositeSubscription();

        mApiWrapper = new ApiWrapper();
        this.view = view;
    }

    protected <E>Subscriber newMySubscriber(final SimpleMyCallBack onNext){
        return new Subscriber<E>() {
            @Override
            public void onCompleted() {
                if (view != null){
                    view.hideLoading();
                }
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
                if (view != null){
                    view.hideLoading();
                }
            }

            @Override
            public void onNext(E t) {
                if (!mCompositeSubscription.isUnsubscribed()){
                    onNext.onNext(t);
                }
            }
        };
    }

    //解绑CompositeSubscription
    public void unsubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

}
