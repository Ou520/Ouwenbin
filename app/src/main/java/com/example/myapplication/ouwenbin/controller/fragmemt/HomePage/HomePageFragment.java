package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage;


import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.myapplication.ouwenbin.controller.fragmemt.BaseFagment;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.recommendfragment.RecommendFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.adapter.MyPagerAdapter;
import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment.AnimationFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.hotfragment.HotFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.livestreamingfragment.LiveStreamingFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.teleplayfragment.TeleplayFragment;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class HomePageFragment extends BaseFagment {
    private TabLayout tabHomePage;//定义TabLayout对象
    private ViewPager vpHomePage;//定义ViewPager对象
    private String[] title = {"直播", "推荐","热门","追番","追剧"};//定义TabLayout的标题( )
    private List<Fragment> fragmentList;//定义集合List<Fragment>
    private View view;

    //Fragment的构造方法
    @SuppressLint("ValidFragment")
    public HomePageFragment() { }



    @Override
    protected View initView() {
        view =View.inflate(mContext, R.layout.fragment_home_page,null);
        initView(view);//调用方法
        return view;
    }


    private void initView(View view) {
        //绑定控件(为控件做初始化工作)
        tabHomePage = view.findViewById(R.id.tabHomePage);
        vpHomePage = view.findViewById(R.id.vpHomePage);

    }

    @Override
    protected void initData() {
        setData();
    }


    private void setData() {
        //为ArrayList<>数组赋值
        fragmentList = new ArrayList<>();

        //为ArrayList<>数组赋值自己创建的Fragment
        fragmentList.add(new LiveStreamingFragment());
        fragmentList.add(new RecommendFragment());
        fragmentList.add(new HotFragment());
        fragmentList.add(new AnimationFragment());
        fragmentList.add(new TeleplayFragment());


        //设置ViewPager适配器
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(),fragmentList,title);
        vpHomePage.setAdapter(adapter);
        vpHomePage.setCurrentItem(1);//设置ViewPager默认显示第几个ViewPager
        //关联ViewPager（有问题）
        tabHomePage.setupWithViewPager(vpHomePage);
    }


}

