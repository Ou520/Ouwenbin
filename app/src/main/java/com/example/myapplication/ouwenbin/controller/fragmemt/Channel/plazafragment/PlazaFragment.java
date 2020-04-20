package com.example.myapplication.ouwenbin.controller.fragmemt.Channel.plazafragment;


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
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.controller.fragmemt.Channel.plazafragment.adaper.MyPlazaAdapter;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PlazaFragment extends Fragment {
    //ButterKnife给控件的初始化
    Unbinder unbinder;
    @BindView(R.id.bnt_Banumi)
    Button bntBanumi;
    @BindView(R.id.bnt_Aimtron)
    Button bntAimtron;
    @BindView(R.id.bnt_VideoHall)
    Button bntVideoHall;
    @BindView(R.id.bnt_Documentary)
    Button bntDocumentary;
    @BindView(R.id.bnt_Cartoon)
    Button bntCartoon;
    @BindView(R.id.bnt_SpecialColumn)
    Button bntSpecialColumn;
    @BindView(R.id.bnt_Live)
    Button bntLive;
    @BindView(R.id.bnt_Frequency)
    Button bntFrequency;
    @BindView(R.id.bnt_Animation)
    Button bntAnimation;
    @BindView(R.id.bnt_Music)
    Button bntMusic;
    @BindView(R.id.bnt_Dance)
    Button bntDance;
    @BindView(R.id.bnt_Game)
    Button bntGame;
    @BindView(R.id.bnt_Science)
    Button bntScience;
    @BindView(R.id.bnt_NumericalCode)
    Button bntNumericalCode;
    @BindView(R.id.bnt_Life)
    Button bntLife;
    @BindView(R.id.bnt_TheDemon)
    Button bntTheDemon;
    @BindView(R.id.bnt_Fashion)
    Button bntFashion;
    @BindView(R.id.bnt_Advertising)
    Button bntAdvertising;
    @BindView(R.id.bnt_Recreation)
    Button bntRecreation;
    @BindView(R.id.bnt_Television)
    Button bntTelevision;
    @BindView(R.id.bnt_Movie)
    Button bntMovie;
    @BindView(R.id.bnt_Teleplay)
    Button bntTeleplay;
    @BindView(R.id.bnt_Wuli)
    Button bntWuli;
    @BindView(R.id.bnt_PhotoAlbum)
    Button bntPhotoAlbum;
    @BindView(R.id.bnt_Vip)
    Button bntVip;
    @BindView(R.id.bnt_TopicCenter)
    Button bntTopicCenter;
    @BindView(R.id.bnt_RankingList)
    Button bntRankingList;
    @BindView(R.id.bnt_ActivityCenter)
    Button bntActivityCenter;
    @BindView(R.id.bnt_SmallDarkRoom)
    Button bntSmallDarkRoom;
    @BindView(R.id.bnt_PlayCenter)
    Button bntPlayCenter;
    @BindView(R.id.bnt_GameCompetition)
    Button bntGameCompetition;
    private RecyclerView recyclerView;//定义RecyclerView对象
    private RefreshLayout refreshLayout;//下拉刷新上拉加载更多
    private MyPlazaAdapter myAdapter;//定义适配器对象

    public PlazaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plaza, container, false);
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
        recyclerView = view.findViewById(R.id.Plaza_recycler);
    }

    private void getData() {
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
        //设置列表的属性
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//recyclerView的显示形式new LinearLayoutManager(this)
        recyclerView.setItemAnimator(new DefaultItemAnimator());//recyclerView的动画效果
        //-------------------------------添加分割线---------------------------------------------------------
//        DividerItemDecoration mDivider=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(mDivider);//给列表添加下划线
        //给recyclerView设置适配器
        myAdapter = new MyPlazaAdapter(getContext(),mediaItems);//给适配器初始化
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

    //按钮的点击事件
    @OnClick({R.id.bnt_Banumi,R.id.bnt_Aimtron, R.id.bnt_VideoHall, R.id.bnt_Documentary, R.id.bnt_Cartoon, R.id.bnt_SpecialColumn, R.id.bnt_Live, R.id.bnt_Frequency, R.id.bnt_Animation, R.id.bnt_Music, R.id.bnt_Dance, R.id.bnt_Game, R.id.bnt_Science, R.id.bnt_NumericalCode, R.id.bnt_Life, R.id.bnt_TheDemon, R.id.bnt_Fashion, R.id.bnt_Advertising, R.id.bnt_Recreation, R.id.bnt_Television, R.id.bnt_Movie, R.id.bnt_Teleplay, R.id.bnt_Wuli, R.id.bnt_PhotoAlbum, R.id.bnt_Vip, R.id.bnt_TopicCenter, R.id.bnt_RankingList, R.id.bnt_ActivityCenter, R.id.bnt_SmallDarkRoom, R.id.bnt_PlayCenter, R.id.bnt_GameCompetition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnt_Banumi:
                Toast.makeText(getContext(), "你点击了1按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Aimtron:
                Toast.makeText(getContext(), "你点击了2按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_VideoHall:
                Toast.makeText(getContext(), "你点击了3按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Documentary:
                Toast.makeText(getContext(), "你点击了4按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Cartoon:
                Toast.makeText(getContext(), "你点击了5按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_SpecialColumn:
                Toast.makeText(getContext(), "你点击了6按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Live:
                Toast.makeText(getContext(), "你点击了7按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Frequency:
                Toast.makeText(getContext(), "你点击了8按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Animation:
                Toast.makeText(getContext(), "你点击了9按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Music:
                Toast.makeText(getContext(), "你点击了10按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Dance:
                Toast.makeText(getContext(), "你点击了11按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Game:
                Toast.makeText(getContext(), "你点击了12按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Science:
                Toast.makeText(getContext(), "你点击了13按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_NumericalCode:
                Toast.makeText(getContext(), "你点击了14按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Life:
                Toast.makeText(getContext(), "你点击了15按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_TheDemon:
                Toast.makeText(getContext(), "你点击了16按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Fashion:
                Toast.makeText(getContext(), "你点击了17按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Advertising:
                Toast.makeText(getContext(), "你点击了18按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Recreation:
                Toast.makeText(getContext(), "你点击了19按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Television:
                Toast.makeText(getContext(), "你点击了20按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Movie:
                Toast.makeText(getContext(), "你点击了21按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Teleplay:
                Toast.makeText(getContext(), "你点击了22按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Wuli:
                Toast.makeText(getContext(), "你点击了23按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_PhotoAlbum:
                Toast.makeText(getContext(), "你点击了24按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_Vip:
                Toast.makeText(getContext(), "你点击了25按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_TopicCenter:
                Toast.makeText(getContext(), "你点击了26按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_RankingList:
                Toast.makeText(getContext(), "你点击了27按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_ActivityCenter:
                Toast.makeText(getContext(), "你点击了28按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_SmallDarkRoom:
                Toast.makeText(getContext(), "你点击了29按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_PlayCenter:
                Toast.makeText(getContext(), "你点击了30按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_GameCompetition:
                Toast.makeText(getContext(), "你点击了31按钮", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
