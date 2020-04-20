package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.hotfragment.adaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.controller.activity.ijkVideoPlayer;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;


public class MyHotAdapter extends RecyclerView.Adapter<MyHotAdapter.ViewHolder> {

    private ArrayList<MediaItem> mediaItems;
    private Context mContext;

    public MyHotAdapter(Context context, ArrayList<MediaItem> mediaItems) {
        mContext = context;
        this.mediaItems = mediaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext, R.layout.hot_item, null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        MediaItem mediaItem= mediaItems.get(position);
        viewHolder.name.setText(mediaItem.getName());
        viewHolder.des.setText("副标题");


        //--------------------------------------------------------------------------------------------------------------------------------------------------------

              /*
                  1.要先在MyViewHolde定义变量和绑定控件先
                  2.可以在这里为Item里的每一个控件设置控件写监听
              */
        viewHolder.LL_Hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ijkVideoPlayer.class);
                //传递列表
                Bundle bundle =new Bundle();
                bundle.putSerializable("videolist",mediaItems);
                intent.putExtras(bundle);
                //传递位置
                intent.putExtra("position",position);
                mContext.startActivity(intent);

                Toast.makeText(mContext, "你点击了布局："+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout LL_Hot;
        NiceImageView image;
        TextView name;
        TextView des;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //给对象赋值
            //给对象赋值
            LL_Hot=itemView.findViewById(R.id.LL_Hot);
            image = itemView.findViewById(R.id.hot_image);
            name = itemView.findViewById(R.id.hot_name);
            des = itemView.findViewById(R.id.hot_des);

        }
    }
}
