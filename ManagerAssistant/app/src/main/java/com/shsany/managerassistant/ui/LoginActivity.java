package com.shsany.managerassistant.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.shsany.managerassistant.PreManager;
import com.shsany.managerassistant.R;
import com.shsany.managerassistant.utils.ToastUtil;

/**
 * Created by PC on 2017/10/17.
 * 账号密码登入界面
 */

public class LoginActivity extends Activity {

    private EditText userid,userpassword;
    private Button login;
    private String name,password;

    private SharedPreferences login_sp;
    private CheckBox mRemember;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addData();
        initView();
    }

    private void addData(){
        login_sp = getSharedPreferences("userInfo",0);
        editor = login_sp.edit();
    }

    private void initView(){
        userid = (EditText) findViewById(R.id.et_userid);
        userpassword = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.tv_login);
        mRemember = (CheckBox)findViewById(R.id.cb_remember);

        String mName = login_sp.getString("USER_NAME","");
        String mPassword = login_sp.getString("PASSWORD","");
        boolean choseRemember = login_sp.getBoolean("mRememberCheck",false);
        if (choseRemember){
            userid.setText(mName);
            userpassword.setText(mPassword);
            mRemember.setChecked(true);

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });



    }

    private void login(){
        name = userid.getText().toString();
        password = userpassword.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast(this,R.string.login_001);
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast(this,R.string.login_002);
            return;
        }
//        final SharedPreferences.Editor editor = login_sp.edit();
        if (name.equals(password)){
            //保存用户名和密码
            editor.putString("USER_NAME",name);
            editor.putString("PASSWORD",password);
            //是否记住密码
            if (mRemember.isChecked()){
                editor.putBoolean("mRememberCheck",true);
            }else{
                editor.putBoolean("mRememberCheck",false);
            }
            editor.commit();
            launchHome();

//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//            finish();

            ToastUtil.showToast(this,R.string.login_003);
        }else {
            ToastUtil.showToast(this,R.string.login_004);
        }

    }

    private void launchHome(){
        PreManager preManager = new PreManager(this);
        preManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }



    //判断edittext输入的账号或密码是否合法
    private void checkInput(Editable editable){
        int length = editable.length();
        if (name == editable.toString()){
            String account = editable.toString();
            if (length == 0){

            }
        }
    }



















    private void queryContactNum(){
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols,null,null,null);
        for (int i = 0 ;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            //获取联系人信息
            int nameIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameIndex);
            String number = cursor.getString(numberIndex);
        }
    }
}
