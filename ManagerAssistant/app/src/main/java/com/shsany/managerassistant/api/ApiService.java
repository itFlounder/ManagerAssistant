package com.shsany.managerassistant.api;

import com.shsany.managerassistant.reponse.Login;
import com.shsany.managerassistant.requestparamete.LoginParams;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by PC on 2017/12/28.
 */

public interface ApiService {
    /**
     * 获取个人信息
     */
    @POST("account/v1/login")
    Observable<Login> getPersonalInfo(@Body LoginParams mLoginParams);
}
