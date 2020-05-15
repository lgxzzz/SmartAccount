package com.smart.account.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.smart.account.R;
import com.smart.account.adpater.HomePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomePageFragment extends Fragment {

    private ViewPager mVp;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private TabLayout mTb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_alarm, container, false);
        initView(view);

        return view;
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initTitile();
        initFragment();
        initData();
        //设置适配器
        mVp.setAdapter(new HomePagerAdapter(getChildFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTb.setupWithViewPager(mVp);
    }

    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("报警");
        mTitleList.add("短信");
        mTitleList.add("WIFI");
        //设置tablayout模式
        mTb.setTabMode(TabLayout.MODE_FIXED);
        //tablayout获取集合中的名称
        mTb.addTab(mTb.newTab().setText(mTitleList.get(0)));
        mTb.addTab(mTb.newTab().setText(mTitleList.get(1)));
        mTb.addTab(mTb.newTab().setText(mTitleList.get(2)));
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(PersonalAccountFragment.getInstance());
        mFragmentList.add(AllPersonAccountFragment.getInstance());
        mFragmentList.add(PersonalSettingFragment.getInstance());
    }


    public void initView(View view){
        mTb = (TabLayout) view.findViewById(R.id.tab_layout);
        mVp = (ViewPager) view.findViewById(R.id.mVp);

    };

    public void initData() {

    }




}
