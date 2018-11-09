package com.wind.androidlearn.network.download;

import java.io.File;

/**
 * 下载信息
 */
public class DownloadInfo {
    public static final int STATUS_LOADING = -1;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAIL = 2;

    public int status;
    public float progress;
    public String url;
    public File file;
}
