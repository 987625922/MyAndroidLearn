package com.wind.wind.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.wind.wind.myapplication.bassis.RecyclerImage.RecycleActivity;
import com.wind.wind.myapplication.bassis.provider.ProviderActivity;
import com.wind.wind.myapplication.butterknifeuse.ButterActivity;
import com.wind.wind.myapplication.fragment.FragmentActivity;
import com.wind.wind.myapplication.ijkplayer.IjkplayActivity;
import com.wind.wind.myapplication.ijkplayer.VideoPlayerActivity;
import com.wind.wind.myapplication.img.BitmapActivity;
import com.wind.wind.myapplication.okhttp.retrofit.GetRequest;
import com.wind.wind.myapplication.screenmatch.DensityActivity;
import com.wind.wind.myapplication.viewpager.ViewpagerActivity;
import com.wyt.zdf.myapplication.R;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_GET_CONTENT = 666;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //recyclerview页面
        findViewById(R.id.btn_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RecycleActivity.class);
                startActivity(intent);
            }
        });
//        BroadcastReceiver广播实现跨进程数据交互
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, com.wind.wind.myapplication.bassis.broadcast.MainActivity.class);
                startActivity(intent);
            }
        });
//        binder跨进程数据交互
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, com.wind.wind.myapplication.bassis.broadcast.binder.MainActivity.class);
                startActivity(intent);
            }
        });
//        content provider实现
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProviderActivity.class);
                startActivity(intent);
            }
        });
        //跳转图片选择
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                }

                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, REQUEST_CODE_GET_CONTENT);
            }
        });
        //retrofit
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GetRequest.class);
                startActivity(intent);
            }
        });
        //bitmap
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BitmapActivity.class);
                startActivity(intent);
            }
        });
//        今日头条适配方案
        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DensityActivity.class);
                startActivity(intent);
            }
        });
        //        fragment使用
        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FragmentActivity.class);
                startActivity(intent);
            }
        });
        //        B站视频播放器使用
        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IjkplayActivity.intentTo(MainActivity.this);
            }
        });
        //       viewpager实现广告页
        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ViewpagerActivity.class);
                startActivity(intent);
            }
        });
        //       分享到微信
        findViewById(R.id.btn11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWeChat("分享到微信");
            }
        });
        //       butter
        findViewById(R.id.btn12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButterActivity.intentTo(MainActivity.this);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存销毁之前的数据

        Log.d("", "onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("", "onRestoreInstanceState");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data == null || data.getData() == null || requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            return;

        final Uri uri = data.getData();
//        final int width = content.getMeasuredWidth() - content.getPaddingLeft() - content.getPaddingRight();
//        content.image(uri, width);
        String url = getRealFilePath(MainActivity.this, uri);
        Log.e("--->", url);
    }


    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 分享到微信
     *
     * @param content 分享内容
     */
    private void shareWeChat(String content){
        //判断是否安装了微信
//        if (isClientInstalled(mActivity,"com.tencent.mm")) {
            Intent weChatIntent = new Intent(Intent.ACTION_SEND);
            weChatIntent.setPackage("com.tencent.mm");
            weChatIntent.setType("text/plain");
            weChatIntent.putExtra(Intent.EXTRA_TEXT, content);
//            mActivity.startActivity(weChatIntent);
//        }else {
//            ToastUtils.showLong("请先安装微信");
//        }
    }


    /**
     * 分享到QQ
     *
     * @param content 分享内容
     */
    private void shareQQ(String content){
//        if (isClientInstalled(mActivity,"com.tencent.mobileqq")) {
            Intent qqIntent = new Intent(Intent.ACTION_SEND);
            qqIntent.setPackage("com.tencent.mobileqq");
            qqIntent.setType("text/plain");
            qqIntent.putExtra(Intent.EXTRA_TEXT, content);
//            mActivity.startActivity(qqIntent);
//        }else {
//            ToastUtils.showLong("请先安装QQ");
//        }
    }


}
