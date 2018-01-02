package com.shsany.managerassistant.ui.login.presenter;

import com.shsany.managerassistant.ui.login.bean.User;
import com.shsany.managerassistant.ui.login.model.LoginModel;
import com.shsany.managerassistant.ui.login.model.LoginModelImpl;
import com.shsany.managerassistant.ui.login.model.OnLoginListener;
import com.shsany.managerassistant.ui.login.view.LoginView;

/**
 * Created by PC on 2017/11/27.
 */

public class LoginPresenterImpl implements LoginPresenter,OnLoginListener {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }
    @Override
    public void loginSuccess(User user) {
        if (loginView != null){
            loginView.navigateToHome(user);
            loginView.hideLoading();
        }
    }

    @Override
    public void loginFailed() {
        if (loginView != null){
            loginView.showFailedError();
            loginView.hideLoading();
        }
    }

    @Override
    public void userNameisEmpty() {
        if (loginView != null){
            loginView.setUserNameisEmpty();
            loginView.hideLoading();
        }
    }

    @Override
    public void passwordisEmpty() {
        if (loginView != null){
            loginView.setPasswordisEmpty();
            loginView.hideLoading();
        }
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (loginView != null){
            loginView.showLoading();
        }
        loginModel.login(username,password,this);
    }

    @Override
    public void onDestory() {
        loginModel = null;
    }
}
