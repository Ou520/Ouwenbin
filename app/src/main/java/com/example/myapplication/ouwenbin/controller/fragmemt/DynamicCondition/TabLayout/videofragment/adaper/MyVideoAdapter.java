package com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.videofragment.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.model.bean.MediaItem;
import com.example.myapplication.ouwenbin.R;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;


public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.ViewHolder> {

    private ArrayList<MediaItem> mediaItems;
    private Context mContext;

    public MyVideoAdapter(Context context, ArrayList<MediaItem> mediaItems) {
        mContext = context;
        this.mediaItems = mediaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext, R.layout.video_item, null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        MediaItem mediaItem= mediaItems.get(position);
        viewHolder.name.setText(mediaItem.getName());
//        viewHolder.tvRecommendClassify.setText(mediaItem.getDesc());


        //--------------------------------------------------------------------------------------------------------------------------------------------------------

              /*
                  1.要先在MyViewHolde定义变量和绑定控件先
                  2.可以在这里为Item里的每一个控件设置控件写监听
              */
        viewHolder. LL_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了布局：" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //定义对象
        LinearLayout LL_Video;
        NiceImageView image, ivVideoHeadPortrait;
        TextView name, des, tvVideoContent, tvVideoImageContent;
        Button bntVideoMenu, bntVideoShare, bntVideoMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //给对象赋值
            LL_Video = itemView.findViewById(R.id.LL_Video);
            image = itemView.findViewById(R.id.ivVideoImage);
            name = itemView.findViewById(R.id.tvVideoName);
            des = itemView.findViewById(R.id.tvVideoTime);
            ivVideoHeadPortrait = itemView.findViewById(R.id.ivVideoHeadPortrait);
            tvVideoContent = itemView.findViewById(R.id.tvVideoContent);
            tvVideoImageContent = itemView.findViewById(R.id.tvVideoImageContent);
            bntVideoMenu = itemView.findViewById(R.id.bntVideoMenu);
            bntVideoShare = itemView.findViewById(R.id.bntVideoShare);
            bntVideoMessage = itemView.findViewById(R.id.bntVideoMessage);

        }
    }
}
