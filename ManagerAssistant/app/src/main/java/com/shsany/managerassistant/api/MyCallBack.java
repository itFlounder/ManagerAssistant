package com.shsany.managerassistant.api;

import com.shsany.managerassistant.reponse.HttpExceptionBean;

/**
 * Created by PC on 2017/12/28.
 */

public interface MyCallBack<T> {

    void onCompleted();

    void onError(HttpExceptionBean httpExceptionBean);

    void onNext(T t);

}
