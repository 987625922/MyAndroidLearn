package com.wind.wind.androidlearn.bassis.RecyclerImage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wyt.zdf.myapplication.R;

import java.util.ArrayList;

//多图recyclerview 解决
public class RecycleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private AdapterRecycler mAdapter;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        mData = new ArrayList<>();

        recyclerView = findViewById(R.id.recycle_main);
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new GridLayoutManager( RecycleActivity.this, 4, GridLayoutManager.VERTICAL, false ) );
        mAdapter = new AdapterRecycler(mData);
        recyclerView.setAdapter(mAdapter);
        //滑动监听，当app在滑动时glide停止
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(RecycleActivity.this).resumeRequests();
                }else {
                    Glide.with(RecycleActivity.this).pauseRequests();
                }
            }
        });
        for (int i = 0 ; i <50 ; i++){
            mData.add("https://desk-fd.zol-img.com.cn/t_s960x600c5/g5/M00/0E/00/ChMkJlnJ4TOIAyeVAJqtjV-XTiAAAgzDAE7v40Amq2l708.jpg");
        }
        mAdapter.updateData(mData);
    }
}
