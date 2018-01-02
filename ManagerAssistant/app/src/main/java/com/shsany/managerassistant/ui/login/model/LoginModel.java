package com.shsany.managerassistant.ui.login.model;

/**
 * Created by PC on 2017/11/27.
 */

public interface LoginModel {
    void login(String username,String password,OnLoginListener listener);

}
