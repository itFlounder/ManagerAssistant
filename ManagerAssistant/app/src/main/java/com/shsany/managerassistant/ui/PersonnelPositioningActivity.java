package com.shsany.managerassistant.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.shsany.managerassistant.R;
import com.shsany.managerassistant.ui.fragment.PositionFragment;
import com.shsany.managerassistant.ui.fragment.QueryFragment;
import com.shsany.managerassistant.ui.fragment.SectionFragment;
import com.shsany.managerassistant.ui.fragment.SubstationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2017/10/20.
 * 人员定位界面，包含5个fragment
 */

public class PersonnelPositioningActivity extends Activity implements View.OnClickListener{
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnelpositioning);
        initView();
    }

    private void initView() {
        LinearLayout position = (LinearLayout)findViewById(R.id.ll_position);
        LinearLayout query = (LinearLayout)findViewById(R.id.ll_query);
        LinearLayout section = (LinearLayout)findViewById(R.id.ll_section);
        LinearLayout statistics = (LinearLayout)findViewById(R.id.ll_statistics);
        LinearLayout substation = (LinearLayout)findViewById(R.id.ll_substation);
        position.setOnClickListener(this);
        query.setOnClickListener(this);
        section.setOnClickListener(this);
        statistics.setOnClickListener(this);
        substation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragemntTransaction = fragmentManager.beginTransaction();
        fragment = new PositionFragment();
        fragemntTransaction.replace(R.id.fm_personnelpositioning,fragment);
        switch (v.getId()){
            case R.id.ll_position:
                fragment = new PositionFragment();
                fragemntTransaction.replace(R.id.fm_personnelpositioning,fragment);
                break;
            case R.id.ll_query:
                fragment = new QueryFragment();
                fragemntTransaction.replace(R.id.fm_personnelpositioning,fragment);
                break;
            case R.id.ll_section:
                fragment = new SectionFragment();
                fragemntTransaction.replace(R.id.fm_personnelpositioning,fragment);
                break;
            case R.id.ll_statistics:
                fragment = new StatisticsFragment();
                fragemntTransaction.replace(R.id.fm_personnelpositioning,fragment);
                break;
            case R.id.ll_substation:
                fragment = new SubstationFragment();
                fragemntTransaction.replace(R.id.fm_personnelpositioning,fragment);
                break;
        }
        fragemntTransaction.commit();


    }
}


