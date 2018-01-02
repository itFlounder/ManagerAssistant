package com.shsany.managerassistant.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.shsany.managerassistant.MyAdapter;
import com.shsany.managerassistant.R;
import com.shsany.managerassistant.utils.ToastUtil;
import com.shsany.managerassistant.view.CircleImageDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2017/10/17.
 * 登入进入的界面
 */

public class HomeActivity extends Activity {
    private List<String> mData1;
    private List<String> mData2;
    private List<String> mData3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }
    public void initView(){
        ListView basedata = (ListView)findViewById(R.id.lv_basedata);
        ListView warning = (ListView)findViewById(R.id.lv_warning);
        ListView message = (ListView)findViewById(R.id.lv_message);
        TextView t1 = (TextView)findViewById(R.id.tv_basedata);
        TextView t2 = (TextView)findViewById(R.id.tv_warning);
        TextView t3 = (TextView)findViewById(R.id.tv_message);
        ImageButton icon = (ImageButton)findViewById(R.id.iv_icon);

        //设置头像圆角
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon2);
        icon.setImageDrawable(new CircleImageDrawable(bitmap));

        mData1 = new ArrayList<>();
        mData2 = new ArrayList<>();
        mData3 = new ArrayList<>();

        getData1();
        getData2();
        getData3();

        MyAdapter myAdapter1 = new MyAdapter(this,mData1);
        MyAdapter myAdapter2 = new MyAdapter(this,mData2);
        MyAdapter myAdapter3 = new MyAdapter(this,mData3);

        basedata.setAdapter(myAdapter1);
        warning.setAdapter(myAdapter2);
        message.setAdapter(myAdapter3);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ResetpwdActivity.class));
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,PersonnelPositioningActivity.class));
                ToastUtil.showToast(getApplicationContext(),R.string.click_title);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,PersonnelPositioningActivity.class));
                ToastUtil.showToast(getApplicationContext(),R.string.click_title);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showToast(getApplicationContext(),R.string.click_title);
                startActivity(new Intent(HomeActivity.this,MessageActivity.class));
            }
        });

        basedata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(getApplication(),R.string.click_item);
            }
        });
        warning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(getApplication(),R.string.click_item);
            }
        });
        message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(getApplication(),R.string.click_item);
            }
        });

    }
    public void getData1(){
        for (int i = 0;i < 50; i++){
            mData1.add("基础数据"+ (i+1));
        }
    }
    public void getData2(){
        for (int i = 0;i < 50; i++){
            mData2.add("安全预警"+ (i+1));
        }
    }
    public void getData3(){
        for (int i = 0;i < 50; i++){
            mData3.add("消息中心"+ (i+1));
        }
    }
}
