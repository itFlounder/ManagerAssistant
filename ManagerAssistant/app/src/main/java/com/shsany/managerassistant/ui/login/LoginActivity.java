package com.shsany.managerassistant.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.shsany.managerassistant.PreManager;
import com.shsany.managerassistant.R;
import com.shsany.managerassistant.ui.HomeActivity;
import com.shsany.managerassistant.ui.login.bean.User;
import com.shsany.managerassistant.ui.login.presenter.LoginPresenter;
import com.shsany.managerassistant.ui.login.presenter.LoginPresenterImpl;
import com.shsany.managerassistant.ui.login.view.LoginView;
import com.shsany.managerassistant.utils.ToastUtil;

/**
 * Created by PC on 2017/11/27.
 */

public class LoginActivity extends Activity implements LoginView{
    private EditText userid,userpassword;
    private Button login;
    private CheckBox mRemember;
    private ProgressBar progressbar;
    private LoginPresenter loginPresenter = new LoginPresenterImpl(this);

    private SharedPreferences login_sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_sp = getSharedPreferences("userInfo",0);
        editor = login_sp.edit();

        initView();
    }

    public void initView(){
        userid = (EditText) findViewById(R.id.et_userid);
        userpassword = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.tv_login);
        mRemember = (CheckBox)findViewById(R.id.cb_remember);
        progressbar = (ProgressBar)findViewById(R.id.pb_progress);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.validateCredentials(userid.getText().toString(),userpassword.getText().toString());
            }
        });


    }

    @Override
    public String getUserName() {
        return userid.getText().toString();
    }

    @Override
    public String getPassword() {
        return userpassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        userid.setText("");
    }

    @Override
    public void clearPassword() {
        userpassword.setText("");
    }

    @Override
    public boolean choseRemember() {
        return login_sp.getBoolean("mRememberCheck",false);
    }

    @Override
    public void setUserNameisEmpty() {
//        ToastUtil.showToast(this,R.string.login_001);
        userid.setError("账号不能为空，请输入账号");
    }

    @Override
    public void setPasswordisEmpty() {
//        ToastUtil.showToast(this,R.string.login_002);
        userpassword.setError("密码不能为空，请输入密码");
    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome(User user) {
        launchHome(user);
        ToastUtil.showToast(this,R.string.login_003);
    }

    @Override
    public void showFailedError() {
        ToastUtil.showToast(this,R.string.login_004);
    }

    private void launchHome(User user){
        PreManager preManager = new PreManager(this);
        preManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestory();
        super.onDestroy();
    }
}
