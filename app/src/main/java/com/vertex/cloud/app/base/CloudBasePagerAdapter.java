package com.vertex.cloud.app.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class CloudBasePagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] mFragmentList;

    public CloudBasePagerAdapter(FragmentManager fm, Fragment... fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList[position];
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.length;
    }

    public void setFragmentList(Fragment[] fragmentList) {
        this.mFragmentList = fragmentList;
    }

}
