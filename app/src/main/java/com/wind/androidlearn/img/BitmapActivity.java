package com.wind.androidlearn.img;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wind.androidlearn.img.imgcache.BitmapDownloadUtil;
import com.wind.androidlearn.img.imgcache.NetBitmapDownloadUtils;
import com.wyt.zdf.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/9/11 0011<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * 待做事情：
 */
public class BitmapActivity extends Activity {
    @BindView(R.id.rl_main)
    RecyclerView rlMain;

    private List<String> list;

    private static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BitmapActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.you_er_bj);
        BitmapUtils.getByteBitmap(bitmap);
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("https://www.android.com/static/2016/img/share/andy-lg.png");
        }

        Adapter adapter = new Adapter(this, list);
        rlMain.setLayoutManager(new GridLayoutManager(this,5));
        rlMain.setAdapter(adapter);

    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holer> {
        private List<String> list;
        private Context context;

        public Adapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public Holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Holer holer = new Holer(LayoutInflater.from(context).inflate(R.layout.item_bitmap,
                    parent, false));
            return holer;
        }

        @Override
        public void onBindViewHolder(@NonNull Holer holder, int position) {
            BitmapDownloadUtil.getInstance().load(holder.img,list.get(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holer extends RecyclerView.ViewHolder {
            ImageView img;

            public Holer(View itemView) {
                super(itemView);
                img = (ImageView) itemView;
            }
        }

    }

}
