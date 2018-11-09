package com.wind.wind.androidlearn.bassis;
/**
 * Created by Administrator on 2018/8/27 0027.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wyt.zdf.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 类 名： AsyncTaskActivity<br>
 * 说 明：<br>
 * 创建日期：2018/8/27 0027<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * Copyright (c) ： by WaiYuTong.版权所有.<br>
 * 待做事情：
 */
public class AsyncTaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("iSpring", "ProviderActivity -> onCreate, Thread name: " + Thread.currentThread().getName());
    }

    public void onClick(View v) {
        //要下载的文件地址
        String[] urls = {
                "http://blog.csdn.net/iispring/article/details/47115879",
                "http://blog.csdn.net/iispring/article/details/47180325",
                "http://blog.csdn.net/iispring/article/details/47300819",
                "http://blog.csdn.net/iispring/article/details/47320407",
                "http://blog.csdn.net/iispring/article/details/47622705"
        };

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(urls);
    }

    //public abstract class AsyncTask<Params, Progress, Result>
    //在此例中，Params泛型是String类型，Progress泛型是Object类型，Result泛型是Long类型
    private class DownloadTask extends AsyncTask<String, Object, Long> {

/**
 * 在AsyncTask执行了execute()方法后就会在UI线程上执行onPreExecute()方法，
 * 该方法在task真正执行前运行，我们通常可以在该方法中显示一个进度条，从而告知用户后台任务即将开始。
 * */
        @Override
        protected void onPreExecute() {
            Log.i("iSpring", "DownloadTask -> onPreExecute, Thread name: " + Thread.currentThread().getName());
            super.onPreExecute();
//            btnDownload.setEnabled(false);
//            textView.setText("开始下载...");
        }
    /***
     * doInBackground会在onPreExecute()方法执行完成后立即执行，该方法用于在工作线程中执行耗时任务
     * */
        @Override
        protected Long doInBackground(String... params) {
            Log.i("iSpring", "DownloadTask -> doInBackground, Thread name: " + Thread.currentThread().getName());
            //totalByte表示所有下载的文件的总字节数
            long totalByte = 0;
            //params是一个String数组
            for(String url: params){
                //遍历Url数组，依次下载对应的文件
                Object[] result = downloadSingleFile(url);
                int byteCount = (int)result[0];
                totalByte += byteCount;
                //在下载完一个文件之后，我们就把阶段性的处理结果发布出去
                publishProgress(result);
                //如果AsyncTask被调用了cancel()方法，那么任务取消，跳出for循环
                if(isCancelled()){
                    break;
                }
            }
            //将总共下载的字节数作为结果返回
            return totalByte;
        }

        //下载文件后返回一个Object数组：下载文件的字节数以及下载的博客的名字
        private Object[] downloadSingleFile(String str){
            Object[] result = new Object[2];
            int byteCount = 0;
            String blogName = "";
            HttpURLConnection conn = null;
            try{
                URL url = new URL(str);
                conn = (HttpURLConnection)url.openConnection();
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int length = -1;
                while ((length = is.read(buf)) != -1) {
                    baos.write(buf, 0, length);
                    byteCount += length;
                }
                String respone = new String(baos.toByteArray(), "utf-8");
                int startIndex = respone.indexOf("<title>");
                if(startIndex > 0){
                    startIndex += 7;
                    int endIndex = respone.indexOf("</title>");
                    if(endIndex > startIndex){
                        //解析出博客中的标题
                        blogName = respone.substring(startIndex, endIndex);
                    }
                }
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            } finally {
                if(conn != null){
                    conn.disconnect();
                }
            }
            result[0] = byteCount;
            result[1] = blogName;
            return result;
        }
/**
 *该方法是在主线程上被调用的，且传入的参数是Progress泛型定义的不定长数组。
 * 如果在doInBackground中多次调用了publishProgress方法，那么主线程就会多次回调onProgressUpdate方法。
 * */
        @Override
        protected void onProgressUpdate(Object... values) {
            Log.i("iSpring", "DownloadTask -> onProgressUpdate, Thread name: " + Thread.currentThread().getName());
            super.onProgressUpdate(values);
            int byteCount = (int)values[0];
            String blogName = (String)values[1];
//            String text = textView.getText().toString();
//            text += "\n博客《" + blogName + "》下载完成，共" + byteCount + "字节";
//            textView.setText(text);
        }
/**
 *
 * 该方法是在主线程中被调用的。当doInBackgroud方法执行完毕后，就表示任务完成了，doInBackgroud方法的返回值就
 * 会作为参数在主线程中传入到onPostExecute方法中，这样就可以在主线程中根据任务的执行结果更新UI。
 * */
        @Override
        protected void onPostExecute(Long aLong) {
            Log.i("iSpring", "DownloadTask -> onPostExecute, Thread name: " + Thread.currentThread().getName());
            super.onPostExecute(aLong);
//            String text = textView.getText().toString();
//            text += "\n全部下载完成，总共下载了" + aLong + "个字节";
//            textView.setText(text);
//            btnDownload.setEnabled(true);
        }

        @Override
        protected void onCancelled() {
            Log.i("iSpring", "DownloadTask -> onCancelled, Thread name: " + Thread.currentThread().getName());
            super.onCancelled();
//            textView.setText("取消下载");
//            btnDownload.setEnabled(true);
        }
    }
}
