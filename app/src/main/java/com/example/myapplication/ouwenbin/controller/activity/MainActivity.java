package com.example.myapplication.ouwenbin.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.ouwenbin.controller.fragmemt.BaseFagment;
import com.example.myapplication.ouwenbin.controller.fragmemt.Channel.ChannelFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.DynamicConditionFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.HomePageFragment;
import com.example.myapplication.ouwenbin.controller.fragmemt.PeopleNearby.PeopleNearbyFragment;
import com.example.myapplication.ouwenbin.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //按键的定义和绑定
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.rl_Game)
    RelativeLayout rl_Game;
    @BindView(R.id.bntGame)
    Button bntGame;
    @BindView(R.id.rl_Download)
    RelativeLayout rl_Download;
    @BindView(R.id.bntDownload)
    Button bntDownload;
    @BindView(R.id.rl_Message)
    RelativeLayout rl_Message;
    @BindView(R.id.bntMessage)
    Button bntMessage;
    @BindView(R.id.bntSearch1)
    Button bntSearch1;
    @BindView(R.id.bntCompile)
    Button bntCompile;
    //文本框的定义和绑定
    @BindView(R.id.tvChannel)
    TextView tvChannel;
    @BindView(R.id.tvDynamicCondition)
    TextView tvDynamicCondition;
    @BindView(R.id.tvPeopleNearby)
    TextView tvPeopleNearby;
    //顶部导航栏（Toolbar）
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    //侧边栏
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private RadioGroup rg_main;
    private List<BaseFagment> fagments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //引用ButterKnife框架来给控件初始化和绑定控件
        ButterKnife.bind(this);
        //初始化数据
        rg_main=findViewById(R.id.rg_main);
        initData();
        setListener();
    }

    private void initData() {
        fagments =new ArrayList<>();
        fagments.add(new HomePageFragment());
        fagments.add(new ChannelFragment());
        fagments.add(new DynamicConditionFragment());
        fagments.add(new PeopleNearbyFragment());
    }




    private void setListener() {

        //顶部导航toolbar本身的点击事件
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        setSupportActionBar(toolbar);//设置toolbar为顶部导航
        //设置导航菜单点击事件
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);//把Drawer的按钮添加到toolbar
        toggle.syncState();
        //侧边栏的点击事件
        navigationView.setNavigationItemSelectedListener(new MyNavigationItemSelectedListener());
        //设置底部导航栏的监听
        rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener());
        rg_main.check(R.id.rb1);

    }

    //底部导航选中的位置
    private int position;
    private Fragment from;
    private class OnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb1:
                    position=0;
                    radioButton(position);
                    break;
                case R.id.rb2:
                    position=1;
                    radioButton(position);
                    break;
                case R.id.rb3:
                    position=2;
                    radioButton(position);
                    break;
                case R.id.rb4:
                    position=3;
                    radioButton(position);
                    break;
                default:
                    position=0;
                    break;
            }

            //根据位置得到对应的Fagment
            BaseFagment tofagment =getFagment();
            //替换Fagment
            switchFagment(from,tofagment);

        }
    }
    private void switchFagment(Fragment fromfagment,Fragment tofagment) {
        if (fromfagment != tofagment){
            from=tofagment;
            //1.得到FragmentManager
            FragmentManager fm =getSupportFragmentManager();
            //2.开启事务
            FragmentTransaction transaction =fm.beginTransaction();
            //才切换
            //判断有没有被添加
            if (!tofagment.isAdded()){
                //没tofagment添加
                //fromfagment隐藏
                if (fromfagment !=null){
                    transaction.hide(fromfagment);
                }
                //添加tofagment
                if (tofagment !=null){
                    transaction.add(R.id.fl_content,tofagment).commit();
                }
            }else {
                //tofagment已经被添加
                //隐藏fromfagment
                if (fromfagment !=null){
                    transaction.hide(fromfagment);
                }
                //显示tofagment
                if (tofagment !=null){
                    transaction.show(tofagment).commit();
                }
            }
        }
    }
    private BaseFagment getFagment() {
        BaseFagment fagment = fagments.get(position);
        return fagment;
    }


    //侧边栏的点击事件
    class MyNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener{

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        //main_drawer菜单的每一项的点击事件
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_homePage:
                    Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Record:
                    Toast.makeText(MainActivity.this, "历史记录", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Download:
                    Toast.makeText(MainActivity.this, "离线下载", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Collect:
                    Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_look:
                    Toast.makeText(MainActivity.this, "稍后再看", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Contribute:
                    Toast.makeText(MainActivity.this, "投稿", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Creation:
                    Toast.makeText(MainActivity.this, "创作中心", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_PopularActivities:
                    Toast.makeText(MainActivity.this, "热门活动", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Live:
                    Toast.makeText(MainActivity.this, "直播中心", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_freeFlow:
                    Toast.makeText(MainActivity.this, "免流量服务", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_orderForm:
                    Toast.makeText(MainActivity.this, "我的订单", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_Member:
                    Toast.makeText(MainActivity.this, "会员中心", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_customerService:
                    Toast.makeText(MainActivity.this, "联系客服", Toast.LENGTH_SHORT).show();
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }
    //---------------------------------侧边列表和底部导航的点击事件---------------------------------------
    public void ivPersonalCenterOnClick(View v) {
        switch (v.getId()) {
            case R.id.ivPersonalCenter:
                //通过SharedPreferences判断是否登录
                SharedPreferences userSetting = getSharedPreferences("user", MODE_PRIVATE);
                String id = userSetting.getString("id", "");
                if (id.equals("")) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 1001);
                    finish();
                } else {
                    Intent intent = new Intent(this, PersonalCenterActivity.class);
                    startActivityForResult(intent, 001);//请求码
                }
                break;
            case R.id.bntWallet:
                Toast.makeText(this, "钱包", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntEwm:
                Toast.makeText(this, "扫码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntMember:
                Toast.makeText(this, "大会员", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntDynamicCondition:
                Toast.makeText(this, "动态", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntAttention:
                Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntFans:
                Toast.makeText(this, "粉丝", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntSetting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntTheme:
                Toast.makeText(this, "主题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntNight:
                Toast.makeText(this, "夜晚", Toast.LENGTH_SHORT).show();
                break;
//---------------------------------------顶部导航的按钮的点击事件---------------------------------------------------------
            case R.id.bntGame:
                Toast.makeText(MainActivity.this, "你点击了游戏按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntDownload:
                Toast.makeText(MainActivity.this, "你点击了下载按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntMessage:
                Toast.makeText(MainActivity.this, "你点击了消息按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvSearch:
                Toast.makeText(MainActivity.this, "你点击了搜索按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntSearch1:
                Toast.makeText(MainActivity.this, "你点击了搜索按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bntCompile:
                Toast.makeText(MainActivity.this, "你点击了编辑按钮", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    //顶部导航控件的状态
    private void radioButton(int position) {
        switch (position){
            case 0:
                //点击相应的单选按钮，显示或者异常相应的组件
                tvSearch.setVisibility(View.VISIBLE);
                tvChannel.setVisibility(View.GONE);
                tvDynamicCondition.setVisibility(View.GONE);
                tvPeopleNearby.setVisibility(View.GONE);
                rl_Game.setVisibility(View.VISIBLE);
                rl_Download.setVisibility(View.VISIBLE);
                rl_Message.setVisibility(View.VISIBLE);
                bntSearch1.setVisibility(View.GONE);
                bntCompile.setVisibility(View.GONE);
                break;
            case 1:
                //点击相应的单选按钮，显示或者异常相应的组件
                tvSearch.setVisibility(View.GONE);
                tvChannel.setVisibility(View.VISIBLE);
                tvDynamicCondition.setVisibility(View.GONE);
                tvPeopleNearby.setVisibility(View.GONE);
                rl_Game.setVisibility(View.INVISIBLE);
                rl_Download.setVisibility(View.VISIBLE);
                rl_Message.setVisibility(View.GONE);
                bntSearch1.setVisibility(View.VISIBLE);
                bntCompile.setVisibility(View.GONE);
                break;
            case 2:
                //点击相应的单选按钮，显示或者异常相应的组件
                tvSearch.setVisibility(View.GONE);
                tvChannel.setVisibility(View.GONE);
                tvDynamicCondition.setVisibility(View.VISIBLE);
                tvPeopleNearby.setVisibility(View.GONE);
                rl_Game.setVisibility(View.GONE);
                rl_Download.setVisibility(View.GONE);
                rl_Message.setVisibility(View.INVISIBLE);
                bntSearch1.setVisibility(View.INVISIBLE);
                bntCompile.setVisibility(View.VISIBLE);
                break;
            case 3:
                //点击相应的单选按钮，显示或者异常相应的组件
                tvSearch.setVisibility(View.GONE);
                tvChannel.setVisibility(View.GONE);
                tvDynamicCondition.setVisibility(View.GONE);
                tvPeopleNearby.setVisibility(View.VISIBLE);
                rl_Game.setVisibility(View.GONE);
                rl_Download.setVisibility(View.GONE);
                rl_Message.setVisibility(View.GONE);
                bntSearch1.setVisibility(View.GONE);
                bntCompile.setVisibility(View.INVISIBLE);
                break;

        }
    }



    private boolean isExit =false;
    @Override
    //判断是否真的想退出软件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (!isExit){
                isExit=true;
                Toast.makeText(this, "在两秒内在按一次就退出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit =false;
                    }
                },2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
