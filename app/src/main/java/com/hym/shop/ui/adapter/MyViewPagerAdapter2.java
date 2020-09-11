package com.hym.shop.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hym.shop.bean.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter2 extends FragmentStatePagerAdapter {

    private List<FragmentInfo> fragmentInfos = new ArrayList<>();

    public MyViewPagerAdapter2(@NonNull FragmentManager fm,List<FragmentInfo> fragmentInfo) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentInfos = fragmentInfo;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentInfos.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentInfos.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentInfos.get(position).getTitle();
    }




}
