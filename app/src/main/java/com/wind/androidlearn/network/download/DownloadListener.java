package com.wind.wind.androidlearn.network.download;

/**
 * 下载监听
 */
public interface DownloadListener {
    /**
     * 下载成功
     */
    void onDownloadSuccess(DownloadInfo downloadInfo);

    /**
     * 下载进度
     */
    void onDownloading(DownloadInfo downloadInfo);

    /**
     * 下载失败
     */
    void onDownloadFailed(DownloadInfo downloadInfo);
}
