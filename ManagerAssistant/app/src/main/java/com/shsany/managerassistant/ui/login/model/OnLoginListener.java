package com.shsany.managerassistant.ui.login.model;

import com.shsany.managerassistant.ui.login.bean.User;

/**
 * Created by PC on 2017/11/27.
 */

public interface OnLoginListener {
    void loginSuccess(User user);
    void loginFailed();
    void userNameisEmpty();
    void passwordisEmpty();
}
