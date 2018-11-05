package com.wyt.hs.zxxtb.network.download;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.wyt.hs.zxxtb.network.schedulers.RxSchedulers;
import com.wyt.hs.zxxtb.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.wyt.hs.zxxtb.network.download.DownloadInfo.STATUS_FAIL;
import static com.wyt.hs.zxxtb.network.download.DownloadInfo.STATUS_LOADING;
import static com.wyt.hs.zxxtb.network.download.DownloadInfo.STATUS_SUCCESS;

/**
 * 下载工具类
 */
public class DownloadUtil {

    private final static String FILE_FOLDER = "wyt/download";
    private final static long FILE_NOT_EXIST = 0L;

    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;
    //存储正在下载的Call，用来取消下载
    private HashMap<String, Call> downCalls;
    //下载监听器
    private HashMap<String,DownloadListener> downloadListeners;

    public static DownloadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
        downloadListeners = new HashMap<>();
    }



    /**
     * @param url      下载连接
     */
    public void download(final String url) {

        final DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.url = url;

        // 创建一个文件
        final File file = getFileByUrl(url);
        Observable.create(new ObservableOnSubscribe<Float>() {
            @Override
            public void subscribe(final ObservableEmitter<Float> emitter) throws Exception {
                //检测之前下载的信息
                final long downloadLength = file.exists() ? file.length() : 0;
                final long contentLength = getContentLength(url);
                //如果文件已经存在并完成，则完成此次任务
                if (downloadLength == contentLength) {
                    if (contentLength == 0) {
                        emitter.onError(new Throwable("下载链接文件不存在"));
                    } else {
                        emitter.onComplete();
                    }
                    return;
                }

                //创建一个请求，并添加断点下载头信息
                Request request = new Request.Builder()
                        .addHeader("Range", "bytes=" + downloadLength + "-" + contentLength)
                        .url(url)
                        .build();
                //执行请求
                Call call = okHttpClient.newCall(request);
                //保存一个请求
                if (downCalls == null) {
                    downCalls = new HashMap<>();
                }
                downCalls.put(url, call);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // 下载失败
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //创建一个短点续传文件，并跳转到上次下次的位置
                        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                        randomAccessFile.seek(downloadLength);

                        //文件传输参数和对象
                        InputStream is;
                        byte[] buf = new byte[1024 * 2];
                        int len;
                        is = response.body().byteStream();

                        //开始下载的位置
                        long sum = downloadLength;

                        //总长度等于剩余下载量加上已下载的量
                        long total = response.body().contentLength() + downloadLength;
//                        LogUtils.d("下载数据","下载前的大小:"+downloadLength  + "  剩余大小:"+response.body().contentLength());
                        while ((len = is.read(buf)) != -1) {
                            randomAccessFile.write(buf, 0, len);
                            sum += len;
                            float progress = sum * 1.0f / total * 100;
                            // 下载中
                            if (downCalls.containsKey(url)){
                                emitter.onNext(progress);
                                //信息打印
                                LogUtils.d("下载中","进度："+progress + " url="+url);
                            }
                        }
                        // 下载完成
                        emitter.onComplete();
                        is.close();
                        randomAccessFile.close();
                    }
                });
            }
        }).compose(RxSchedulers.<Float>compose()).subscribe(new Observer<Float>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Float progress) {
                downloadInfo.status = STATUS_LOADING;
                downloadInfo.progress = progress;
                if (downloadListeners.get(url)!=null) {
                    downloadListeners.get(url).onDownloading(downloadInfo);
                }
            }

            @Override
            public void onError(Throwable e) {
                downloadInfo.status = STATUS_FAIL;
                downloadInfo.progress = 0;
                if (downloadListeners.get(url)!=null) {
                    downloadListeners.get(url).onDownloadFailed(downloadInfo);
                }
            }

            @Override
            public void onComplete() {
                downloadInfo.status = STATUS_SUCCESS;
                downloadInfo.progress = 100;
                downloadInfo.file = file;
                if (downloadListeners.get(url)!=null) {
                    downloadListeners.get(url).onDownloadSuccess(downloadInfo);
                }
            }
        });
    }

    /**
     * 设置下载监听
     * @param url
     * @param listener
     */
    public void setDownloadListener(String url,DownloadListener listener){
        downloadListeners.put(url,listener);
    }

    /**
     * 下载前获取文件下载进度
     *
     * @return 下载百分比(%)
     */
    @SuppressLint("CheckResult")
    public void getPercentBeforeDownload(final String url, Consumer<Integer> consumer) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 创建一个文件
                String savePath = isExistDir(FILE_FOLDER);
                final File file = new File(savePath, getNameFromUrl(url));

                //检测之前下载的信息
                final long downloadLength = file.exists() ? file.length() : 0;
                final long contentLength = getContentLength(url);
                LogUtils.d("下载信息", "downloadLength=" + downloadLength + " contentLength=" + contentLength);
                if (contentLength == 0) {
                    emitter.onNext(0);
                } else {
                    emitter.onNext((int) (downloadLength * 1.0f / contentLength * 1.0f * 100));
                }
            }
        }).compose(RxSchedulers.<Integer>compose()).subscribe(consumer);
    }

    /**
     * 获取文件信息
     */
    public File getFileByUrl(String url) {
        // 创建一个文件
        String savePath;
        try {
            savePath = isExistDir(FILE_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new File(savePath, getNameFromUrl(url));
    }

    /**
     * 检测是否正在下载
     * @return
     */
    public boolean checkLoading(String url){
        if (url == null || url.isEmpty()) {
            return false;
        }
        if (downCalls != null) {
            Call call = downCalls.get(url);
            return call != null;
        }
        return false;
    }

    /**
     * 取消下载
     */
    public void cancel(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        if (downCalls != null) {
            Call call = downCalls.get(url);
            if (call != null) {
                call.cancel();
            }
            downCalls.remove(url);
        }
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * 获取文件长度
     */
    private long getContentLength(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                LogUtils.d("检测长度", "下载前:" + contentLength);
                response.close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FILE_NOT_EXIST;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
