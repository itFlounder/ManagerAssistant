package com.shsany.managerassistant.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shsany.managerassistant.R;

/**
 * Created by PC on 2017/11/10.
 */

public class MyListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message,container);
        return view;
    }

    public static MyListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        MyListFragment fragment = new MyListFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
