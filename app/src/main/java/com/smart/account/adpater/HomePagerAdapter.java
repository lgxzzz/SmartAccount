package com.smart.account.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {
    //添加fragment的集合
    private List<Fragment> mFragmentList;
    //添加标题的集合
     private List<String> mTilteLis;

    public HomePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> tilteLis) {
        super(fm);
        mFragmentList = fragmentList;
        mTilteLis = tilteLis;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //获取标题
    @Override
    public CharSequence getPageTitle(int position) {

        return mTilteLis.get(position);
    }
}

