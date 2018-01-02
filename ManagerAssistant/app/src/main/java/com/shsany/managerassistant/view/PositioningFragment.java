package com.shsany.managerassistant.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shsany.managerassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2017/11/10.
 */

public class PositioningFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static final int ONE=0;
    public static final int TWO=1;
    public static final int THREE=2;
    public static final int FOUR=3;
    public static final int FIVE=4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_positioning,container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        mTabLayout.addTab(mTabLayout.newTab().setText("1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("3"));
        mTabLayout.addTab(mTabLayout.newTab().setText("4"));
        mTabLayout.addTab(mTabLayout.newTab().setText("5"));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(MyListFragment.newInstance(ONE),"1");
        adapter.addFragment(MyListFragment.newInstance(TWO),"2");
        adapter.addFragment(MyListFragment.newInstance(THREE),"3");
        adapter.addFragment(MyListFragment.newInstance(FOUR),"4");
        adapter.addFragment(MyListFragment.newInstance(FIVE),"5");
        mViewPager.setAdapter(adapter);

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragment = new ArrayList<>();
        private final List<String > mFragmentTitle = new ArrayList<>();

        public void addFragment(Fragment fragment ,String title){
            mFragment.add(fragment);
            mFragmentTitle.add(title);
        }

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitle.get(position);
        }
    }
}
