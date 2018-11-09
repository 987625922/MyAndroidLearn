package com.wind.androidlearn.bassis.RecyclerImage;
/**
 * Created by Administrator on 2018/8/28 0028.
 */

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wyt.zdf.myapplication.R;

import java.util.ArrayList;

/**
 * 类 名： AdapterRecycler<br>
 * 说 明：<br>
 * 创建日期：2018/8/28 0028<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    private ArrayList<String> mData;

    public AdapterRecycler(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
//        holder.mTv.setText(mData.get(position));
        Glide.with( holder.mTv )
                .load( mData.get(position) )
                .apply(
                        RequestOptions.encodeFormatOf( Bitmap.CompressFormat.PNG )
                                .diskCacheStrategy( DiskCacheStrategy.ALL )
                ).into( holder.mTv );

    }

    @Override
    public int getItemCount() {
        Log.e("--->",mData.size()+"");
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (ImageView) itemView.findViewById(R.id.photo);
        }
    }

}
