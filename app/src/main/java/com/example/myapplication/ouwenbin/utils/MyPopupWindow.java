package com.example.myapplication.ouwenbin.utils;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.myapplication.ouwenbin.R;
import com.example.myapplication.ouwenbin.utils.view.CircleColorButton;

/*
    设置控制面板的弹窗*/
public class MyPopupWindow {
    private Context mContext;
    private PopupWindow popupWindow;
    private RelativeLayout media_controller;
    private EditText addDanmaku;
    private CircleColorButton colorButton;
    private RadioGroup rg_Size;
    private RadioGroup rg_Location;
    private RadioGroup rg_Color;
    private Button bnt_Show;

    public MyPopupWindow(Context mContext,RelativeLayout media_controller) {
        this.mContext = mContext;
        this.media_controller=media_controller;
    }

    public void PopupWindow( String Tag){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow;

        //设置PopupWindow的布局样式
        if (Tag.equals("Coin")){
            vPopupWindow = inflater.inflate(R.layout.popupwindow_coin_layout, null, false);//引入弹窗布局
            //绑定自定义样式布局里的控件
            //定义PopupWindow视图(设置popupWindow的大小)
            //PopupWindow(View contentView, int width, int height, boolean focusable)
            //ActionBar.LayoutParams.WRAP_CONTENT,(根据内容来决定大小)
            //ActionBar.LayoutParams.MATCH_PARENT（为铺满）
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        } else if (Tag.equals("Share")){
            vPopupWindow = inflater.inflate(R.layout.popupwindow_share_layout, null, false);//引入弹窗布局
            popupWindow = new PopupWindow(vPopupWindow,ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT, true);

        }else if (Tag.equals("Menus")){
            vPopupWindow = inflater.inflate(R.layout.popupwindow_menus_layout, null, false);//引入弹窗布局
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, true);

        }else if (Tag.equals("DanmakuSet")){
            vPopupWindow = inflater.inflate(R.layout.popupwindow_danmakuset_layout, null, false);//引入弹窗布局
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, true);

        }else if (Tag.equals("AddDanmaku")){
            vPopupWindow = inflater.inflate(R.layout.popupwindow_adddanmaku_layout, null, false);//引入弹窗布局
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
            initDataforAddDanmaku(vPopupWindow);
        }else if (Tag.equals("ResolutionRatio")){
            vPopupWindow = inflater.inflate(R.layout.popupwindow_resolutionratio_layout, null, false);//引入弹窗布局
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT ,ActionBar.LayoutParams.MATCH_PARENT, true);
        }

        //设置PopupWindow进出动画
        if (Tag.equals("AddDanmaku")){
            popupWindow.setAnimationStyle(R.style.AddDanmaku_PopupWindowAnimation);
       }else if (Tag.equals("Share")){
            popupWindow.setAnimationStyle(R.style.Share_PopupWindowAnimation);
        }else {
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        }

        //设置PopupWindow显示的位置（引入（父布局）依附的布局）
        View parentView = LayoutInflater.from(mContext).inflate(R.layout.activity_ijk_video_player, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        if (Tag.equals("AddDanmaku")){
            popupWindow.showAtLocation(parentView, Gravity.TOP, 0, 0);//方法内有四个参数:
        }else if (Tag.equals("Share")){
            popupWindow.showAtLocation(parentView, Gravity.BOTTOM ,0, 0);//方法内有四个参数:
        }else {
            popupWindow.showAtLocation(parentView, Gravity.RIGHT, 0, 0);//方法内有四个参数:
        }


        //关闭PopupWindow后的操作
        popupWindow.setOnDismissListener(new MyDismissListener());


    }




    /*---------------------------------PopupWindow里布局对应的监听-------------------------------------------------------*/
    //关闭PopupWindow后的操作的监听
    class MyDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            media_controller.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    media_controller.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }

    //发送弹幕布局控件的初始化
    private void initDataforAddDanmaku(View vPopupWindow) {
        //绑定按钮
        ImageView closePopupwindow = vPopupWindow.findViewById(R.id.iv_return);
        Button sendDanmaku = vPopupWindow.findViewById(R.id.bnt_sendDanmaku);
        addDanmaku = vPopupWindow.findViewById(R.id.et_addDanmaku);
        colorButton =vPopupWindow.findViewById(R.id.iv_color);
        colorButton.setColor(Color.WHITE);//设置colorButton的颜色

        RadioButton rb_SmallSize =vPopupWindow.findViewById(R.id.rb_SmallSize);
        RadioButton rb_BigSize =vPopupWindow.findViewById(R.id.rb_BigSize);
        RadioButton rb_Top =vPopupWindow.findViewById(R.id.rb_Top);
        RadioButton rb_LR =vPopupWindow.findViewById(R.id.rb_LR);
        RadioButton rb_Bottom =vPopupWindow.findViewById(R.id.rb_Bottom);
        bnt_Show =vPopupWindow.findViewById(R.id.bnt_Show);
        //颜色的单选按钮
        RadioButton rb_White =vPopupWindow.findViewById(R.id.rb_White);
        RadioButton rb_Red =vPopupWindow.findViewById(R.id.rb_Red);
        RadioButton rb_Yellow =vPopupWindow.findViewById(R.id.rb_Yellow);
        RadioButton rb_Green =vPopupWindow.findViewById(R.id.rb_Green);
        RadioButton rb_Blue =vPopupWindow.findViewById(R.id.rb_Blue);
        RadioButton rb_Pink =vPopupWindow.findViewById(R.id.rb_Pink);
        RadioButton rb_LightGreen =vPopupWindow.findViewById(R.id.rb_LightGreen);
        RadioButton rb_DarkBlue =vPopupWindow.findViewById(R.id.rb_DarkBlue);
        RadioButton rb_Gold =vPopupWindow.findViewById(R.id.rb_Gold);
        RadioButton rb_Purple =vPopupWindow.findViewById(R.id.rb_Purple);
        RadioButton rb_LightBLue =vPopupWindow.findViewById(R.id.rb_LightBLue);
        RadioButton rb_Olive =vPopupWindow.findViewById(R.id.rb_Olive);

        rg_Size =vPopupWindow.findViewById(R.id.rg_Size);
        rg_Location =vPopupWindow.findViewById(R.id.rg_Location);
        rg_Color =vPopupWindow.findViewById(R.id.rg_Color);

        //为布局里的按钮设置监听（其他控件的操作类）
        closePopupwindow.setOnClickListener(new MyPopupwindowbntClickListener());
        sendDanmaku.setOnClickListener(new MyPopupwindowbntClickListener());
        bnt_Show .setOnClickListener(new MyPopupwindowbntClickListener());
        rb_SmallSize.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_BigSize.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Top.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_LR.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Bottom.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_White.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Red.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Yellow.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Green.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Blue.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Pink.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_LightGreen.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_DarkBlue.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Gold.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Purple.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_LightBLue.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb_Olive.setOnCheckedChangeListener(new MyCheckedChangeListener());
    }

    //发弹幕布局里按钮的监听
    boolean RadioGroupShow=true;
    private String TYPE_SCROLL ="TYPE_SCROLL_RL";
    private String danmakuTextSize ="中号";
    class MyPopupwindowbntClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_return:
                    popupWindow.dismiss();
                    break;
                case R.id.bnt_sendDanmaku:
                    break;
                case R.id.bnt_Show:
                    if (RadioGroupShow){
                        rg_Size.setVisibility(View.GONE);
                        rg_Location.setVisibility(View.GONE);
                        rg_Color.setVisibility(View.VISIBLE);
                        bnt_Show.setBackgroundResource(R.drawable.ic_jianto2);
                        RadioGroupShow=false;
                    }else {
                        rg_Size.setVisibility(View.VISIBLE);
                        rg_Location.setVisibility(View.VISIBLE);
                        rg_Color.setVisibility(View.GONE);
                        bnt_Show.setBackgroundResource(R.drawable.ic_jianto);
                        RadioGroupShow=true;
                    }
                    break;
            }
        }
    }
    //发弹幕布局里单选按钮的监听
    class MyCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int ID =buttonView.getId();
            if (ID==R.id.rb_SmallSize &&isChecked){
                danmakuTextSize ="小号";
                Toast.makeText(mContext, "小号", Toast.LENGTH_SHORT).show();
            }else if (ID==R.id.rb_BigSize &&isChecked){
                danmakuTextSize ="大号";
                Toast.makeText(mContext, "大号", Toast.LENGTH_SHORT).show();
            }else if (ID==R.id.rb_Top &&isChecked){
                TYPE_SCROLL="TYPE_FIX_TOP";
                Toast.makeText(mContext, "头部", Toast.LENGTH_SHORT).show();
            }else if(ID==R.id.rb_LR &&isChecked){
                TYPE_SCROLL="TYPE_SCROLL_LR";
                Toast.makeText(mContext, "左右", Toast.LENGTH_SHORT).show();
            }else if(ID==R.id.rb_Bottom &&isChecked){
                TYPE_SCROLL="TYPE_FIX_BOTTOM";
                Toast.makeText(mContext, "底部", Toast.LENGTH_SHORT).show();
            }else if(ID==R.id.rb_White &&isChecked){
                colorButton.setColor(Color.rgb(255,255,255));
            }else if(ID==R.id.rb_Red &&isChecked){
                colorButton.setColor(Color.rgb(	255,0,0));
            }
            else if(ID==R.id.rb_Yellow &&isChecked){
                colorButton.setColor(Color.rgb(255,255,0));
            }
            else if(ID==R.id.rb_Green &&isChecked){
                colorButton.setColor(Color.rgb(0,128,0));
            }
            else if(ID==R.id.rb_Blue &&isChecked){
                colorButton.setColor(Color.rgb(0,191,255));
            }
            else if(ID==R.id.rb_Pink &&isChecked){
                colorButton.setColor(Color.rgb(	255,192,203));
            }
            else if(ID==R.id.rb_LightGreen &&isChecked){
                colorButton.setColor(Color.rgb(	144,238,144));
            }
            else if(ID==R.id.rb_DarkBlue &&isChecked){
                colorButton.setColor(Color.rgb(	0,0,139));
            }
            else if(ID==R.id.rb_Gold &&isChecked){
                colorButton.setColor(Color.rgb(	255,215,0));
            }
            else if(ID==R.id.rb_Purple &&isChecked){
                colorButton.setColor(Color.rgb(	128,0,128));
            }
            else if(ID==R.id.rb_LightBLue &&isChecked){
                colorButton.setColor(Color.rgb(	173,216,230));
            }
            else if(ID==R.id.rb_Olive &&isChecked){
                colorButton.setColor(Color.rgb(	128,128,0));
            }
        }
    }



}
