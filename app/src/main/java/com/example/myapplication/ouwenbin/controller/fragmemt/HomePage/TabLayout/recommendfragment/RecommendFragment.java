package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.recommendfragment;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.recommendfragment.adaper.MyRecommendAdapter;
import com.example.myapplication.ouwenbin.R;
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


public class RecommendFragment extends Fragment {


   private RecyclerView recyclerView;//定义RecyclerView对象
   private MyRecommendAdapter myRecommendAdapter;
   private RefreshLayout refreshLayout;//下拉刷新上拉加载更多
    //图片轮播
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_recommend, container, false);
        initView(view);//调用方法
        getData();
        setData();
        setListener();
        return view;

    }

    private void initView(View view)
    {
        banner = view.findViewById(R.id.banner1);
        //变量的初始化工作
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView=view.findViewById(R.id.recycler);
    }

    private void getData() {
        getDataFromLocal();

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
//---------------------------------------列表---------------------------------
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//VERTICAL：垂直方向的滚动,HORIZONTAL水平方向的滚动
        recyclerView.setLayoutManager(gridLayoutManager);//recyclerView的显示形式new LinearLayoutManager(this)
        recyclerView.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        recyclerView.setNestedScrollingEnabled(false);
        //给recyclerView设置适配器
        myRecommendAdapter =new MyRecommendAdapter(getContext(),mediaItems);//给适配器初始化
        recyclerView.setAdapter(myRecommendAdapter);

    }

    private void setListener() {
        //设置图片轮播的监听
        banner.setOnBannerListener(new MyOnBannerClick());

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
                getDataFromLocal();
                myRecommendAdapter.notifyDataSetChanged();
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
    class MyOnBannerClick implements OnBannerListener{
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

}
