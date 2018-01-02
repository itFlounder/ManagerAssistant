package com.shsany.managerassistant.ui.login.view;

import com.shsany.managerassistant.ui.login.bean.User;

/**
 * Created by PC on 2017/11/27.
 */

public interface LoginView {

    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    boolean choseRemember();

    void setUserNameisEmpty();

    void setPasswordisEmpty();

    void showLoading();

    void hideLoading();

    void navigateToHome(User user);

    void showFailedError();


}
