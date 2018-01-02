package com.shsany.managerassistant.api;

import com.shsany.managerassistant.reponse.HttpExceptionBean;

/**
 * Created by PC on 2017/12/28.
 * MyCallBack 的一个简单实现，onNext（） 方法一定要重写，onCompleted()和onError 更具需要重写
 */

public abstract class SimpleMyCallBack<T> implements MyCallBack<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(HttpExceptionBean httpExceptionBean) {

    }
}
