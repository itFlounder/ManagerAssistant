package com.shsany.managerassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shsany.managerassistant.R;
import com.shsany.managerassistant.base.BaseFragment;

/**
 * Created by PC on 2017/11/13.
 */

public class SectionFragment extends BaseFragment {

    private RecyclerView section;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        section = (RecyclerView)mContentView.findViewById(R.id.lv_section);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        section.setLayoutManager(layoutManager);
//        section.setAdapter();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected int onSetLayoutId() {
        return R.layout.fragment_section;
    }

    @Override
    public void onClick(View v) {

    }
}
