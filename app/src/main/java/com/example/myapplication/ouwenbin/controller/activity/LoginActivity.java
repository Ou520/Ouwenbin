package com.example.myapplication.ouwenbin.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.model.db.UsersService;
import com.example.myapplication.ouwenbin.model.bean.Users;

public class LoginActivity extends Activity {
    SharedPreferences mySetting;
    SharedPreferences.Editor editor;
    ToggleButton tb_Login;
    EditText etLogin1,etLogin2;
    Button bntLogin3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bntLogin3=findViewById(R.id.bntLogin3);

        tb_Login=findViewById(R.id.tb_Login);
        etLogin1=findViewById(R.id.etLogin1);
        etLogin2=findViewById(R.id.etLogin2);

        //--------------------------隐藏和显示EditText里的字符串------------------------------
        tb_Login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    etLogin2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    etLogin2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }

    //注册的点击事件
    public void bntLoginRegOnClick(View v)
    {
        Intent intent = new Intent(this, RegActivity.class);
        startActivityForResult(intent, 1003);
    }
    //返回按钮的点击事件
    public void bntLoginReturnClick(View v)
    {
        startActivity(new Intent(this,MainActivity.class));

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

    //登录按钮的点击事件
    public void bntLoginOnClick(View v)
    {
        String useName=etLogin1.getText().toString();//把EditText输入的内容转换为字符串
        String pwd=etLogin2.getText().toString();
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

        UsersService service=new UsersService(this);
        Users users =service.getUsersByUserName(useName);
        //和数据库做校验，判断用户名和密码是否一样
        if (users!=null&& pwd.equals(users.getUserPwd()) /*useName.equals(userId)&&pwd.equals(userPwd)*/)
        {
            mySetting=getSharedPreferences("user",MODE_PRIVATE);
            editor=mySetting.edit();
            editor.putString("id",useName);
            editor.apply();
            setResult(1002,new Intent().putExtra("id",useName));
            Toast.makeText(this, "登录成功！！！", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(this,MainActivity.class),1001);
            finish();
        }else
        {
            Toast.makeText(this, "用户或名密码不正确！！！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    //Intent的数据传输处理
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003&&resultCode==1004) {
            if (data!=null)
            {
                //接收注册页面传回来的数据，并显示出来
                String userName=data.getStringExtra("id");
                String pwd=data.getStringExtra("pwd");
                etLogin1.setText(userName);
                etLogin2.setText(pwd);
            }
        }
    }
}

