package com.learn.learn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.learn.learn.bassis.fragment.FragmentActivity;
import com.learn.learn.bassis.provider.ProviderActivity;
import com.learn.learn.bassis.viewpager.ViewpagerActivity;
import com.learn.learn.cache.DiskLruCacheActivity;
import com.learn.learn.eventbus.EventBusActivity;
import com.learn.learn.jetpack.LifecycleLearnActivity;
import com.learn.learn.room.RoomActivity;
import com.wyt.common.utils.StatusBarUtils;
import com.wyt.common.utils.ToastUtil;

import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 学习首页
 */
@Route(path = "/learn/main")
public class MainActivity extends Activity {
    private static final int REQUEST_CODE_GET_CONTENT = 666;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StatusBarUtils.setTransparent(getWindow());

//        BroadcastReceiver广播实现跨进程数据交互
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,
                        com.learn.learn.bassis.broadcast.MainActivity.class);
                startActivity(intent);
            }
        });
//        binder跨进程数据交互
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,
                        com.learn.learn.bassis.broadcast.binder.MainActivity.class);
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
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                }

                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, REQUEST_CODE_GET_CONTENT);
            }
        });
        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiskLruCacheActivity.intentTo(MainActivity.this);
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
        //room的使用
        findViewById(R.id.btn13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomActivity.intentTo(MainActivity.this);
            }
        });

        findViewById(R.id.btn15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LifecycleLearnActivity.intentTo(MainActivity.this);
            }
        });
        findViewById(R.id.btn16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusActivity.intentTo(MainActivity.this);
            }
        });
        findViewById(R.id.btn17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.learn.learn.bassis.broadcast.binder.MainActivity.intentTo(MainActivity.this);
            }
        });
        findViewById(R.id.btnBluetoothUse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.learn.learn.bassis.蓝牙.MainActivity.intentTo(MainActivity.this);
            }
        });
        findViewById(R.id.btn18).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                com.learn.learn.插件化宿主首页.MainActivity.intentTo(MainActivity.this);
            }
        });
    }

    /**
     * 横竖屏切换会回调，数据保存
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存销毁之前的数据

        Log.d("", "onSaveInstanceState");

    }

    /**
     * 横竖屏切换会回调，数据获取
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("", "onRestoreInstanceState");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data == null || data.getData() == null ||
                requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            return;
        }

        final Uri uri = data.getData();
//        final int width = content.getMeasuredWidth() - content.getPaddingLeft() - content
// .getPaddingRight();
//        content.image(uri, width);
        String url = getRealFilePath(MainActivity.this, uri);
        Log.e("--->", url);
    }


    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
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
    private void shareWeChat(String content) {
        //判断是否安装了微信
        if (isClientInstalled(MainActivity.this, "com.tencent.mm")) {
            Intent weChatIntent = new Intent(Intent.ACTION_SEND);
            weChatIntent.setPackage("com.tencent.mm");
            weChatIntent.setType("text/plain");
            weChatIntent.putExtra(Intent.EXTRA_TEXT, content);
            MainActivity.this.startActivity(weChatIntent);
        } else {
            ToastUtil.showLong(MainActivity.this, "请先安装微信");
        }
    }


    /**
     * 分享到QQ
     *
     * @param content 分享内容
     */
    private void shareQQ(String content) {
        if (isClientInstalled(MainActivity.this, "com.tencent.mobileqq")) {
            Intent qqIntent = new Intent(Intent.ACTION_SEND);
            qqIntent.setPackage("com.tencent.mobileqq");
            qqIntent.setType("text/plain");
            qqIntent.putExtra(Intent.EXTRA_TEXT, content);
            MainActivity.this.startActivity(qqIntent);
        } else {
            ToastUtil.showLong(MainActivity.this, "请先安装QQ");
        }
    }

    /**
     * 使用Intent queryIntentActivities 判断
     */
    public static boolean isClientInstalled(Context context, String appPackageName) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setPackage(appPackageName);
        i.setType("image/*");
        PackageManager pm = context.getPackageManager();
        @SuppressLint("WrongConstant") List<?> ris = pm.queryIntentActivities(i,
                PackageManager.GET_ACTIVITIES);
        return ris != null && ris.size() > 0;
    }

}
