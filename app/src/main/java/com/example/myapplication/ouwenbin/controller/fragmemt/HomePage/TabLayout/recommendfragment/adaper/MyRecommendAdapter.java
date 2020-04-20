package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.recommendfragment.adaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.ouwenbin.controller.activity.ijkVideoPlayer;
import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.shehuan.niv.NiceImageView;
import java.util.ArrayList;



public class MyRecommendAdapter extends RecyclerView.Adapter<MyRecommendAdapter.ViewHolder> {

    private ArrayList<MediaItem> mediaItems;
    private Context mContext;

    public MyRecommendAdapter(Context context, ArrayList<MediaItem> mediaItems) {
        mContext = context;
        this.mediaItems = mediaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext, R.layout.lesson_item, null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        MediaItem mediaItem= mediaItems.get(position);
        viewHolder.tvRecommendContent.setText(mediaItem.getName());
//        viewHolder.tvRecommendClassify.setText(mediaItem.getDesc());


        //--------------------------------------------------------------------------------------------------------------------------------------------------------

              /*
                  1.要先在MyViewHolde定义变量和绑定控件先
                  2.可以在这里为Item里的每一个控件设置控件写监听
              */
            viewHolder.LL_Recommend.setOnClickListener(new View.OnClickListener() {
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout LL_Recommend;
        NiceImageView ivRecommendImage;
        TextView tvRecommendContent, tvRecommendClassify;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //给对象赋值
            LL_Recommend = itemView.findViewById(R.id.LL_Recommend);
            ivRecommendImage = itemView.findViewById(R.id.ivRecommendImage);
            tvRecommendContent = itemView.findViewById(R.id.tvRecommendContent);
            tvRecommendClassify = itemView.findViewById(R.id.tvRecommendClassify);

        }
    }
}
