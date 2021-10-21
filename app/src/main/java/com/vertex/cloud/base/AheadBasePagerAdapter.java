package com.vertex.cloud.base;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class AheadBasePagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] mFragmentList;

    public AheadBasePagerAdapter(FragmentManager fm, Fragment... fragmentList) {
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
