package com.shsany.managerassistant.ui.login.model;

import android.os.Handler;
import android.text.TextUtils;

import com.shsany.managerassistant.ui.login.bean.User;

/**
 * Created by PC on 2017/11/27.
 */

public class LoginModelImpl implements LoginModel {
    @Override
    public void login(final String username, final String password, final OnLoginListener listener) {
        //登录时耗时操作，子线程中完成
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(username)){
                    listener.userNameisEmpty();
                }
                if (TextUtils.isEmpty(password)){
                    listener.passwordisEmpty();
                }
                if ("1234".equals(username) && "1234".equals(password)){
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    listener.loginSuccess(user);
                }else {
                    listener.loginFailed();
                }
            }
        },2*1000);
    }
}
