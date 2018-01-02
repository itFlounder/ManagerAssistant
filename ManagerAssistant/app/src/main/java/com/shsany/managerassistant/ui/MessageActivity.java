package com.shsany.managerassistant.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shsany.managerassistant.MyAdapter;
import com.shsany.managerassistant.R;
import com.shsany.managerassistant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2017/10/20.
 * 点击home界面 基础数据，安全预警，消息中心时进入消息条目显示界面
 */

public class MessageActivity extends Activity {
    private List<String> mData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }
    public void initView(){
        ListView message = (ListView)findViewById(R.id.lv_message1);
        mData = new ArrayList<>();
        for (int i = 1; i<50; i++){
            mData.add("消息中心"+i);
        }
        MyAdapter myAdapter = new MyAdapter(this,mData);
        message.setAdapter(myAdapter);
        message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(getApplicationContext(),R.string.click_item);
            }
        });

    }
}
