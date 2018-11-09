package com.wind.androidlearn.network.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 下载服务类
 *
 * @author JT
 */
public final class DownloadService extends Service {


    private DownloadBinder downloadBinder;
    private DownloadListener downloadListener;

    @Override
    public void onCreate() {
        super.onCreate();
        downloadBinder = new DownloadBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    /**
     * DownloadBinder
     */
    public final class DownloadBinder extends Binder {

        public void setDownLoadListener(String url, DownloadListener listener) {
            DownloadUtil.getInstance().setDownloadListener(url, listener);
        }

        public void startDownload(final String url) {
            DownloadUtil.getInstance().download(url);
        }

        public void pauseDownload(String url) {
            DownloadUtil.getInstance().cancel(url);
        }

        public void cancelDownload(String url) {
            DownloadUtil.getInstance().cancel(url);
        }
    }
}
