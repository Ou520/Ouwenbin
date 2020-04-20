package com.example.myapplication.ouwenbin.controller.fragmemt.Channel.plazafragment.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;


public class MyPlazaAdapter extends RecyclerView.Adapter<MyPlazaAdapter.ViewHolder> {

    private ArrayList<MediaItem> mediaItems;
    private Context mContext;

    public MyPlazaAdapter(Context context, ArrayList<MediaItem> mediaItems) {
        mContext = context;
        this.mediaItems = mediaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext, R.layout.plaza_item, null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        MediaItem mediaItem= mediaItems.get(position);
        viewHolder.tvPlazaChannel.setText(mediaItem.getName());
//        viewHolder.tvRecommendClassify.setText(mediaItem.getDesc());


        //--------------------------------------------------------------------------------------------------------------------------------------------------------

              /*
                  1.要先在MyViewHolde定义变量和绑定控件先
                  2.可以在这里为Item里的每一个控件设置控件写监听
              */

        viewHolder.LL_Plaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了布局：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.rbSubscription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    viewHolder.rbSubscription.setTextOff(" + 订阅");
                }
                if (isChecked==false){
                    viewHolder.rbSubscription.setTextOn("已订阅");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout LL_Plaza;
        NiceImageView ivPlazaImage1, ivPlazaImage2;
        TextView tvPlazaChannel, tvPlazaSubheading, tvPlazaContent1, tvPlazaContent2;
        ToggleButton rbSubscription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //给对象赋值
            LL_Plaza = itemView.findViewById(R.id.LL_Plaza);
            ivPlazaImage1 = itemView.findViewById(R.id.ivPlazaImage1);
            tvPlazaChannel = itemView.findViewById(R.id.tvPlazaChannel);
            tvPlazaSubheading = itemView.findViewById(R.id.tvPlazaSubheading);
            ivPlazaImage2 = itemView.findViewById(R.id.ivPlazaImage2);
            tvPlazaContent1 = itemView.findViewById(R.id.tvPlazaContent1);
            tvPlazaContent2 = itemView.findViewById(R.id.tvPlazaContent2);
            rbSubscription = itemView.findViewById(R.id.rbSubscription);

        }
    }
}
