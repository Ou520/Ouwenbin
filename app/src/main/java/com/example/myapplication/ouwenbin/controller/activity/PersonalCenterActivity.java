package com.example.myapplication.ouwenbin.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.R;

public class PersonalCenterActivity extends Activity {
    TextView tv_msg;
    SharedPreferences userSetting;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        tv_msg=findViewById(R.id.tv_msg);

        userSetting=getSharedPreferences("user",MODE_PRIVATE);
        String userId=userSetting.getString("id","");
        tv_msg.setText("用户名:"+"\n"+" "+userId);
    }
    public void dealUser(View view)
    {
        switch (view.getId())
        {
            case R.id.bntQuit:
                userSetting=getSharedPreferences("user",MODE_PRIVATE);
                editor=userSetting.edit();
//                editor.remove("userId"); //只删除userId的数据，不删除其他
                editor.clear();//删除所有数据
                editor.apply();
                Toast.makeText(this, "退出成功！！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, 0010);//请求码
                break;
            case R.id.bntBack:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 强制竖屏设置
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
