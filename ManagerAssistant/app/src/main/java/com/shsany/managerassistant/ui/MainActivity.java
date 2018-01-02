package com.shsany.managerassistant.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shsany.managerassistant.PreManager;
import com.shsany.managerassistant.R;

/**
 * Created by PC on 2017/10/17.
 * 此界面判断进入是进入登录界面，还是home界面
 */
public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 2*1000;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PreManager preManager = new PreManager(MainActivity.this);
                //判断是否第一次运行
                if (preManager.isFirstTimeLaunch()){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));

                    finish();

                }else{
                    preManager.setIsFirstTimeLaunch(false);
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));

                    finish();
                }
            }
        },SPLASH_DISPLAY_LENGHT);
    }
}
