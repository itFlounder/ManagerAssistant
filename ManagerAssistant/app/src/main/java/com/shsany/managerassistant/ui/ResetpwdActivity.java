package com.shsany.managerassistant.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.shsany.managerassistant.R;
import com.shsany.managerassistant.utils.ToastUtil;

/**
 * Created by PC on 2017/10/31.
 * 修改密码界面
 */

public class ResetpwdActivity extends Activity {
    private EditText mAccount,mPwd_old,mPwd_new,mPwd_check;
    private Button mSure,mCancel;
    private String account,pwd_old,pwd_new,pwd_check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        initview();
    }
    public void initview(){
        mAccount = (EditText)findViewById(R.id.resetpwd_edit_name);
        mPwd_old = (EditText)findViewById(R.id.resetpwd_edit_pwd_old);
        mPwd_new = (EditText)findViewById(R.id.resetpwd_edit_pwd_new);
        mPwd_check = (EditText)findViewById(R.id.resetpwd_edit_pwd_check);
        mSure = (Button)findViewById(R.id.resetpwd_btn_sure);
        mCancel = (Button)findViewById(R.id.resetpwd_btn_cancel);
    }

    /**
     * 点击取消按钮
     */
    public void not_to_reset(){

    }

    /**
     * 点击确认按钮
     */
    public void sure_to_reset(){
        account = mAccount.getText().toString();
        pwd_old = mPwd_old.getText().toString();
        pwd_new = mPwd_new.getText().toString();
        pwd_check = mPwd_check.getText().toString();
        int result = 1;//检测用户名密码是否匹配
        if (result == 1){
            if (pwd_new.equals(pwd_old)){
                ToastUtil.showToast(this,R.string.resetpwd_008);
                return;
            }else if (!pwd_new.equals(pwd_check)){
                ToastUtil.showToast(this,R.string.resetpwd_009);
                return;
            }else {
                boolean flag = true;//判断密码是否修改成功
                if(flag == false){
                    ToastUtil.showToast(this,R.string.resetpwd_010);
                    return;
                }else{
                    ToastUtil.showToast(this,R.string.resetpwd_011);



                    finish();
                }
            }
        }else /*if (result == 0)*/{
            ToastUtil.showToast(this,R.string.resetpwd_012);
            return;
        }

    }
    /**
     * 判断用户是否存在，密码是否正确，密码是否有效
     */
    public boolean isUserNameAndPwdValid(){
        int count = 1;//检测用户名是否存在
        if (count <= 0){
            ToastUtil.showToast(this,R.string.resetpwd_003);
            return false;
        }
        if (account.equals("")){
            ToastUtil.showToast(this,R.string.resetpwd_004);
            return false;
        }else if (pwd_old.equals("")){
            ToastUtil.showToast(this,R.string.resetpwd_005);
            return false;
        }else if (pwd_new.equals("")){
            ToastUtil.showToast(this,R.string.resetpwd_006);
            return false;
        }else if (pwd_check.equals("")){
            ToastUtil.showToast(this,R.string.resetpwd_007);
            return false;
        }
        return true;
    }
}
