package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment.adaper.RVAdapter;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment.adaper.RVAdapter1;
import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment.adaper.AnimationBangumicCassifyAdapter;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment.adaper.AnimationTopAdapter;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;


public class AnimationFragment extends Fragment {
    private ArrayList<String> mDatas = new ArrayList<>();
    private ArrayList<String> mDatas2 = new ArrayList<>();
    private ArrayList<String> mDatas3 = new ArrayList<>();
    private ArrayList<String> mDatas4 = new ArrayList<>();
    RecyclerView recyclerView,Animation_rv,Animation_rv2,Animation_rv3;
    private RefreshLayout refreshLayout;//下拉刷新上拉加载更多
    //图片轮播
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    public AnimationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_animation, container, false);
        initView(view);//调用方法
        getData();
        setData();
        setListener();
        return view;
    }

    private void initView(View view) {
        //变量的初始化工作
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.Animation_recycler1);
        Animation_rv=view.findViewById(R.id.Animation_rv);
        Animation_rv2=view.findViewById(R.id.Animation_rv2);
        Animation_rv3=view.findViewById(R.id.Animation_rv3);
        banner =view.findViewById(R.id.banner1);
    }
    private void getData() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        list_path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560961309866&di=b99e3de52f15530c1337db2308a3e692&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201604%2F29%2F20160429125320_yfRUH.jpeg");
        list_path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560961353383&di=2b6f1554c6be9e79520d02281fb80e05&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F22%2F20151122221259_nahry.jpeg");
        list_path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560954539327&di=c95341b9f01bd83f6541b75b7ad3b8f5&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-4o3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F37d3d539b6003af3de8986413c2ac65c1038b6e4.jpg");
        list_path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560954369705&di=2048c2be749fdaba4a9c75592319a5ba&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F19a2a8d28a84324b726f84f1508851682cfb98ea.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("天天向上");
        generateDatas();
        RvDatas();
        RvDatas2();
        RvDatas4();

    }

    private void setData() {
        //---------------------------------图片轮播--------------------------
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                //必须最后调用的方法，启动轮播图。
                .start();
        //头部的类表
        //第一个列表（我的追番）
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggered);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        AnimationTopAdapter adapter=new AnimationTopAdapter(getContext(),mDatas);
        recyclerView.setAdapter(adapter);
        //第二个列表（热门榜单）
        StaggeredGridLayoutManager staggered1=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        Animation_rv.setLayoutManager(staggered1);
        Animation_rv.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        RVAdapter rvAdapter=new RVAdapter(getContext(),mDatas2);
        Animation_rv.setAdapter(rvAdapter);
        //第三个列表（即将开播）
        StaggeredGridLayoutManager staggered3=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        Animation_rv3.setLayoutManager(staggered3);
        Animation_rv3.setItemAnimator(new DefaultItemAnimator());
        RVAdapter1 rvAdapter1=new RVAdapter1(getContext(),mDatas4);
        Animation_rv3.setAdapter(rvAdapter1);
        //第四个列表(番剧分类)
        StaggeredGridLayoutManager staggered2=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        Animation_rv2.setLayoutManager(staggered2);
        Animation_rv2.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        DividerItemDecoration mDivider=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Animation_rv2.addItemDecoration(mDivider);//给列表添加下划线
        AnimationBangumicCassifyAdapter rv2Adapter=new AnimationBangumicCassifyAdapter(getContext(),mDatas3);
        Animation_rv2.setAdapter(rv2Adapter);

    }

    private void setListener() {
        banner.setOnBannerListener(new MyOnBannerListener());

        //设置RefreshLayout的属性
        //设置 Header 样式:
        refreshLayout.setRefreshHeader(new BezierCircleHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        //下拉刷新的监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "刷新成功！！", Toast.LENGTH_SHORT).show();

                //建议使用异步任务

                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

            }
        });

        //上拉加载的监听
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "加载成功！！", Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


    }




    //轮播图的监听方法
    class MyOnBannerListener implements OnBannerListener{
        @Override
        public void OnBannerClick(int position) {
            Toast.makeText(getContext(), "你点了第"+position+"张轮播图", Toast.LENGTH_SHORT).show();
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    private void generateDatas() {
        for (int i = 0; i < 10; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }

    private void RvDatas() {
        for (int i = 0; i < 3; i++) {
            mDatas2.add("第 " + i + " 个item");
        }
    }

    private void RvDatas2() {
        for (int i = 0; i < 14; i++) {
            mDatas3.add("类型*" + i + " *番剧");
        }
    }

    private void RvDatas4() {
        for (int i = 0; i < 4; i++) {
            mDatas4.add("番剧" + i + "名");
        }
    }
}
