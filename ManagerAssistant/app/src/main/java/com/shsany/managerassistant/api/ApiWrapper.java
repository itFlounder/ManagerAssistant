package com.shsany.managerassistant.api;

import com.shsany.managerassistant.reponse.Login;
import com.shsany.managerassistant.requestparamete.LoginParams;

import rx.Observable;

/**
 * Created by PC on 2017/12/28.
 * Api类的包装
 */

public class ApiWrapper extends Api {

    public Observable<Login> getUserInfo(LoginParams loginParams){
        return applySchedulers(getService().getPersonalInfo(loginParams));
    }
}
