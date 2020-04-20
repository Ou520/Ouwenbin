package com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.myapplication.ouwenbin.controller.fragmemt.BaseFagment;
import com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.hotfragment.DcHotFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.synthesizefragment.SynthesizeFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.videofragment.VideoFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.adapter.MyPagerAdapter;
import com.example.myapplication.ouwenbin.R;

import java.util.ArrayList;
import java.util.List;


public class DynamicConditionFragment extends BaseFagment {

    TabLayout tabDynamicCondition;//定义TabLayout对象
    ViewPager vpDynamicCondition;//定义ViewPager对象
    private String[] title = {"视频","综合","热门"};//定义TabLayout的标题（ ）
    private List<Fragment> fragmentList;//定义动态数组List<Fragment>
    private View view;

    //构造方法
    public DynamicConditionFragment() { }

    @Override
    protected View initView() {
        view =View.inflate(mContext, R.layout.fragment_dynamic_condition,null);
        initView(view);//调用方法
        return view;
    }

    @Override
    protected void initData() {
        setData();
    }


    private void initView(View view) {
        //绑定控件(为控件做初始化工作)
        tabDynamicCondition = view.findViewById(R.id.tabDynamicCondition);
        vpDynamicCondition = view.findViewById(R.id.vpDynamicCondition);

    }

    private void setData() {
        //为ArrayList<>数组赋值
        fragmentList = new ArrayList<>();

        //为ArrayList<>数组赋值自己创建的Fragment
        fragmentList.add(new VideoFragment());
        fragmentList.add(new SynthesizeFragment());
        fragmentList.add(new DcHotFragment());

        //设置ViewPager适配器
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(),fragmentList,title);
        vpDynamicCondition.setAdapter(adapter);//设置ViewPager的适配器
        vpDynamicCondition.setCurrentItem(1);//设置ViewPager默认显示第几个ViewPager
        //关联ViewPager
        tabDynamicCondition.setupWithViewPager(vpDynamicCondition);
    }


}
