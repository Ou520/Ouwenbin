package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.hotfragment;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.hotfragment.adaper.MyHotAdapter;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;




public class HotFragment extends Fragment {
   private RecyclerView recyclerView;//定义RecyclerView对象
   private MyHotAdapter myAdapter;//定义适配器对象
    private RefreshLayout refreshLayout;//下拉刷新上拉加载更多


    public HotFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_hot, container, false);
        initView(view);//调用方法
        getData();
        setData();
        setListener();
        return view;
    }


    private void initView(View view)
    {
        //变量的初始化工作
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView=view.findViewById(R.id.hot_recycler);

    }


    private void getData() {
        //数据的获取
        getDataFromLocal();


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
        //设置RecyclerView的属性
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//recyclerView的显示形式new LinearLayoutManager(this)
        recyclerView.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        //给recyclerView设置适配器
        myAdapter=new MyHotAdapter(getContext(),mediaItems);//给适配器初始化
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

                getDataFromLocal();
                myAdapter.notifyDataSetChanged();
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



}
