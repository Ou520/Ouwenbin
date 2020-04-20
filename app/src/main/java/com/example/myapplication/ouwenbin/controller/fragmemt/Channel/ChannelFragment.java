package com.example.myapplication.ouwenbin.controller.fragmemt.Channel;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.myapplication.ouwenbin.controller.fragmemt.BaseFagment;
import com.example.myapplication.ouwenbin.controller.fragmemt.Channel.plazafragment.PlazaFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.adapter.MyPagerAdapter;
import com.example.myapplication.ouwenbin.R;
import java.util.ArrayList;
import java.util.List;


public class ChannelFragment extends BaseFagment {
    private TabLayout tabChannel;//定义TabLayout对象
    private ViewPager vpChannel;//定义ViewPager对象
    private String[] title = {"广场"};//定义TabLayout的标题
    private List<Fragment> fragmentList;//定义动态数组List<Fragment>
    private View view;

    public ChannelFragment() {}


    @Override
    protected View initView() {
        view =View.inflate(mContext, R.layout.fragment_channel,null);
        initView(view);//调用方法
        return view;
    }

    @Override
    protected void initData() {
        getData();
        setData();
        setListener();
    }

    private void initView(View view) {
        //绑定控件(为控件做初始化工作)
        tabChannel = view.findViewById(R.id.tabChannel);
        vpChannel = view.findViewById(R.id.vpChannel);
    }

    private void getData() {
        //为ArrayList<>数组赋值
        fragmentList = new ArrayList<>();
        //创建Fragment
        fragmentList.add(new PlazaFragment());
    }

    private void setData() {
        //设置ViewPager适配器
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(),fragmentList,title);
        vpChannel.setAdapter(adapter);
//        vpChannel.setCurrentItem(1);//设置ViewPager默认显示第几个ViewPager
        //关联ViewPager（有问题）
        tabChannel.setupWithViewPager(vpChannel);
    }

    private void setListener() {

    }

}
