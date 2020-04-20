package com.example.myapplication.ouwenbin.controller.fragmemt.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
//设置ViewPager适配器
public class MyPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    String[] title;
    //构造方法
    public MyPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,String[] title) {
        super(fm);
        this.fragmentList=fragmentList;
        this.title=title;
    }
    //下面的是FragmentPagerAdapter类里的实现方法
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
