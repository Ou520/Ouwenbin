package com.example.myapplication.ouwenbin.controller.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.utils.permission.PermissionUtils;
import com.example.myapplication.ouwenbin.utils.permission.request.IRequestPermissions;
import com.example.myapplication.ouwenbin.utils.permission.request.RequestPermissions;
import com.example.myapplication.ouwenbin.utils.permission.requestresult.IRequestPermissionsResult;
import com.example.myapplication.ouwenbin.utils.permission.requestresult.RequestPermissionsResultSetApp;

public class LaunchActivity extends Activity {
    private Button bntJump;
    //调用自己写的IRequestPermissions接口，通过接口调用相应的方法
    private IRequestPermissions requestPermissions = RequestPermissions.getInstance();//动态权限请求
    private IRequestPermissionsResult requestPermissionsResult = RequestPermissionsResultSetApp.getInstance();//动态权限请求结果处理

    private boolean biaoji =true;
    int index=7;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setData();
        setListener();

  }

    //通过Handler()方法来接收子线程传回来的消息（Message），并在相应的组件上显示
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==010)//通过msg.what判断消息是来自那个字线程的，并在UI线程的组件显示或者其他操作
            {
                int index=msg.arg1;//获取消息携带的属性值
                bntJump.setText("跳转："+index);
            }

        }
    };


    private void initData() {
        setContentView(R.layout.activity_launch);
        bntJump=findViewById(R.id.bntJump);

        //动态获取系统权限
        if (!requestPermissions())//判断系统是否已经执行requestPermissions()，如果没有就让他去执行，如果执行了就开始执行别的操作
        {
            return;
        }
    }

    private void setData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (biaoji){
                    Intent intent =new Intent(LaunchActivity .this, MainActivity.class);
                    //设置Activity的启动模式（可以在AndroidManifest.xml中设置，也可以在Intent中设置）
                    /*
                       Intent.FLAG_ACTIVITY_NEW_TASK：“singleTask”启动模式
                       Intent.FLAG_ACTIVITY_SINGLE_TOP  ："singleTop"启动模式
                       Intent.FLAG_ACTIVITY_CLEAR_TOP ："singleTask"效果相同
                       Intent.FLAG_ACTIVITY_NO_HISTORY ：使用该模式来启动Activity，当该Activity启动其他Activity后，该Activity就被销毁了，不会保留在任务栈中。
                       Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS ：使用该标识位启动的Activity不添加到最近应用列表，也即我们从最近应用里面查看不到我们启动的这个activity。与属性android:excludeFromRecents="true"效果相同。
                    */
//                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);//设置Activity的切换动画（第一参数：进入时的动画；第二个参数：退出时的动画）
                    finish();
                }
            }
        }, 7000);

        //创建子线程，实现子线程（Thread）里的Runnable接口并实现接口里的run()方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (index>=1)
                {
                    Message msg=new Message();//创建Message对象
                    //给对象指定参数
                    msg.what=010;//标志（整型变量），用来判断消息是来自那个字线程
                    msg.arg1=index;//拿来传整型（int）数据和msg.arg2一样
                    handler.sendMessage(msg);//发送信息
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    index--;
                }
            }
        }).start();
    }

    private void setListener() {
        //给bntJump按钮写监听，实现页面跳转
        bntJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biaoji=false;
                startActivityForResult(new Intent(LaunchActivity.this, MainActivity.class), 1);
                index=0;
                finish();

            }
        });
    }


    /*-------------动态获取安卓系统的权限，第一步在AndroidManifest.xml里申请相应的权限;第二步是在String[] permissions里给出你申请的权限---------------------*/
    //请求权限
    private boolean requestPermissions() {
        //需要请求的权限
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_CONTACTS
        };
        //开始请求权限
        return requestPermissions.requestPermissions(
                this,
                permissions,
                PermissionUtils.ResultCode1
        );
    }

    //用户授权操作结果（可能授权了，也可能未授权）
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //用户给APP授权的结果
        //判断grantResults是否已全部授权，如果是，执行相应操作，如果否，提醒开启权限
        if (requestPermissionsResult.doRequestPermissionsResult(this, permissions, grantResults)) {
            //输出授权结果
            Toast.makeText(LaunchActivity.this, "授权成功，请开始使用吧！", Toast.LENGTH_LONG).show();
            //请求的权限全部授权成功，此处可以做自己想做的事了
            biaoji=false;
            startActivityForResult(new Intent(LaunchActivity.this, MainActivity.class), 1);
            index=0;

        } else {
            //输出授权结果
            Toast.makeText(LaunchActivity.this, "请给APP授权，否则功能无法正常使用！", Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);//设置Activity的切换动画（第一参数：进入时的动画；第二个参数：退出时的动画）
    }
}
