package com.smart.account;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import com.smart.account.adpater.HomePagerAdapter;
import com.smart.account.bean.User;
import com.smart.account.fragment.AllPersonAccountFragment;
import com.smart.account.fragment.PersonalAccountFragment;
import com.smart.account.fragment.PersonalSettingFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivtiy {
    public static MainActivity _this;
    private User mUser;

    private ViewPager mVp;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private TabLayout mTb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        _this = this;

    }

    public void init(){
        initView();
        initTitile();
        initFragment();
        initData();
        //设置适配器
        mVp.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTb.setupWithViewPager(mVp);
    }

    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("个人账本");
        mTitleList.add("所有账本");
        mTitleList.add("个人中心");
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


    public void initView(){
        mTb = (TabLayout) findViewById(R.id.tab_layout);
        mVp = (ViewPager) findViewById(R.id.mVp);

    };

    public void initData() {

    }



}
