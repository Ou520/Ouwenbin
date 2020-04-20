package com.example.myapplication.ouwenbin.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.model.db.UsersService;
import com.example.myapplication.ouwenbin.model.bean.Users;

public class RegActivity extends Activity {

    SharedPreferences mySetting,userSetting;
    SharedPreferences.Editor editor;
    EditText et1,et2,et3;
    ToggleButton tb1,tb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);

        tb1=findViewById(R.id.tb1);
        tb2=findViewById(R.id.tb2);


        //--------------------------隐藏和显示EditText里的字符串------------------------------
        tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    et2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    et2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
        //实现密码的隐藏和显示
        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    et3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    et3.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
        //------------------------------------结束----------------------------------------
    }
    //注册按键的点击事件
    public void regBnttonOnChick(View v) {
        switch (v.getId()) {
            case R.id.bnt1:
                //判断EditText是否为空并给出提示
                String useName=et1.getText().toString();//把EditText输入的内容转换为字符串
                String pwd=et2.getText().toString();
                String rgpwd=et3.getText().toString();
                if (useName.equals("")||useName.equals(null))//判断EditText是否为空并给出提示
                {
                    Toast.makeText(this, "用户名不允许为空！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.equals("")||pwd.equals(null))
                {
                    Toast.makeText(this, "密码不允许为空！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rgpwd.equals("")||rgpwd.equals(null))
                {
                    Toast.makeText(this, "确认密码允许为空！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rgpwd.equals(pwd))
                {
                    //把数据存储在XML文件中
                    SharedPreferences userSetting=getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor =userSetting.edit();
                    editor.putString("id",useName);
                    editor.putString("pwd",pwd);
                    editor.apply();


                    //把数据插入数据库中
                    Users users=new Users(useName,pwd);
                    UsersService service=new UsersService(this);
                    if (service.add(users))
                    {
                        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                        //把数据传到登录页面
                        Intent intent=new Intent();
                        intent.putExtra("id",useName);
                        intent.putExtra("pwd",pwd);
                        setResult(1004,intent);
                        finish();
                    }else
                    {
                        Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
                    }

                }else
                {
                    Toast.makeText(this, "两次密码不一致！！！", Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
            case R.id.bnt2:
                //清空EditText输入的内容
                et1.setText("");
                et2.setText("");
                et3.setText("");
                break;
            case R.id.bnt3:
                finish();//关闭Activity
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
