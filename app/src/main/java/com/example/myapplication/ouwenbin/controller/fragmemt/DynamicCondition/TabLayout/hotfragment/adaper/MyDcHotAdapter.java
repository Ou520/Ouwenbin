package com.example.myapplication.ouwenbin.controller.fragmemt.DynamicCondition.TabLayout.hotfragment.adaper;

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


public class MyDcHotAdapter extends RecyclerView.Adapter<MyDcHotAdapter.ViewHolder> {

    private ArrayList<MediaItem> mediaItems;
    private Context mContext;

    public MyDcHotAdapter(Context context, ArrayList<MediaItem> mediaItems) {
        mContext = context;
        this.mediaItems = mediaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = View.inflate(mContext, R.layout.dc_hot_item, null);
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
        viewHolder.LL_DcHot.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout LL_DcHot;
        NiceImageView ivDcHotHeadPortrait, image;
        TextView name, des, tvDcHotContent;
        Button bntDcHotShare, bntDcHotMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //给对象绑定控件
            LL_DcHot = itemView.findViewById(R.id.LL_DcHot);
            image = itemView.findViewById(R.id.dcHotImage);
            name = itemView.findViewById(R.id.dcHotName);

            ivDcHotHeadPortrait = itemView.findViewById(R.id.ivDcHotHeadPortrait);
            tvDcHotContent = itemView.findViewById(R.id.tvDcHotContent);
            bntDcHotShare = itemView.findViewById(R.id.bntDcHotShare);
            bntDcHotMessage = itemView.findViewById(R.id.bntDcHotMessage);

        }
    }
}
