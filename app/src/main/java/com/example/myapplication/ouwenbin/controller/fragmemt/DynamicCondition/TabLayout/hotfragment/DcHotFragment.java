package com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.hotfragment;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.hotfragment.adaper.DcHotTopAdapter;
import com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.hotfragment.adaper.MyDcHotAdapter;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DcHotFragment extends Fragment {

    Unbinder unbinder;

    private ArrayList<String> mDatas = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView, rv;//定义RecyclerView对象
    private RefreshLayout refreshLayout;//下拉刷新上拉加载更多
    private MyDcHotAdapter myAdapter;//定义适配器对象
    private DcHotTopAdapter adapter;//定义适配器对象


    public DcHotFragment() {//构造方法

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dc_hot, container, false);
        initView(view);
        getData();
        setData();
        setListener();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {
        //变量的初始化工作
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.dcHot_recycler);
        rv = view.findViewById(R.id.dcHot_recycler1);

    }


    private void getData() {
        getDataFromLocal();
        generateDatas();
        //调用工具类的getData方法
    }


    //从本地的sdcard得到数据
    //通过子线程，从内容提供者里面获取视频
    //存放视频的数据集合
    private ArrayList<MediaItem> mediaItems;
    private void getDataFromLocal() {
        mediaItems =new ArrayList<>();//初始化数组（用之前一定要初始化（new））
        new Thread(){
            @Override
            public void run() {
                super.run();
                ContentResolver resolver=getContext().getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs={
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard的名字
                        MediaStore.Video.Media.DURATION,//视频总长
                        MediaStore.Video.Media.SIZE,//视频文件的大小
                        MediaStore.Video.Media.DATA,//视频的决对地址
                        MediaStore.Video.Media.ARTIST,//歌曲的演唱者
                };
                Cursor cursor =resolver.query(uri,objs,null,null,null);
                if (cursor !=null){
                    while (cursor.moveToNext()){
                        MediaItem mediaItem = new MediaItem();

                        mediaItems.add(mediaItem);//写在上面和写在下面是一样的
                        //一个视频的信息
                        String name = cursor.getString(0);//视频的名称
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频的时长
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);//视频的文件大小
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);//视频的播放地址
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);//艺术家
                        mediaItem.setArtist(artist);
                    }
                    cursor.close();//释放cursor
                }
            }
        }.start();
    }


    private void setData() {
        //设置头部的列表的属性
        StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        rv.setLayoutManager(staggered);//设置列表的滑动方向和样式
        rv.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        adapter = new DcHotTopAdapter(getContext(), mDatas);
        rv.setAdapter(adapter);//设置适配器
        //----------------------------------------------------------------------------------------------------------
        //设置内容区的类表的属性
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//recyclerView的显示形式new LinearLayoutManager(this)
        recyclerView.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        myAdapter = new MyDcHotAdapter(getContext(),mediaItems);//给适配器初始化
        recyclerView.setAdapter(myAdapter);
    }

    private void setListener() {
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    private void generateDatas() {
        for (int i = 0; i < 30; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }

}
