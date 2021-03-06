package com.example.myapplication.ouwenbin.controller.fragmemt.HomePage.TabLayout.animationfragment.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ouwenbin.R;

import java.util.ArrayList;

public class AnimationBangumicCassifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    int mCreatedHolder=0;
    private NormalHolder normalHolder;

    public AnimationBangumicCassifyAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTV;
        public ImageView mImg;

        public NormalHolder(View itemView) {
            super(itemView);

            mTV = itemView.findViewById(R.id.video_name1);
            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mTV.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            mImg = itemView.findViewById(R.id.CoverFlowAdapter);
            mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mTV.getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mCreatedHolder++;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.animation_bangumi_ccassify_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position));
        //设置图片
//        normalHolder.mImg.setBackground(mContext.getResources().getDrawable(mPics[position%mPics.length]));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}

