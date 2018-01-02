package com.shsany.managerassistant.ui.login.presenter;

/**
 * Created by PC on 2017/11/27.
 */

public interface LoginPresenter {
    void validateCredentials(String username,String password);
    void onDestory();
}
