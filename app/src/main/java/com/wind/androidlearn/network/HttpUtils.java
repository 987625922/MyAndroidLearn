package com.wind.wind.androidlearn.network;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.wind.wind.androidlearn.bassis.Utils.LogUtil;
import com.wind.wind.androidlearn.network.schedulers.RxSchedulers;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.wind.wind.androidlearn.network.converter.Base64Utils.MD5;

/**
 * 无加密的网络请求工具
 */
public class HttpUtils {

    private static HttpUtils mHttpUils;
    private OkHttpClient mOkHttpClient;

    /**
     * 单例模式获取网络参数
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (mHttpUils == null) {
            mHttpUils = new HttpUtils();
        }
        return mHttpUils;
    }

    private HttpUtils() {
        mOkHttpClient = new OkHttpClient();
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    @SuppressLint("CheckResult")
    public void get(final String url, final Map<String, String> params, final CallBack<String> callBack) {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> observableEmitter) throws Exception {
                Request request = new Request.Builder().url(url + getParams(params))
                        .get().build();
                Call call = mOkHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    observableEmitter.onNext(response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    observableEmitter.onError(e1);
                }
            }
        }).compose(RxSchedulers.<Response>compose())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(@NonNull Response response) throws Exception {
                        callBack.onSuccess(response.body().string());
                        response.body().close();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        callBack.onFail(throwable);
                    }
                });
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    @SuppressLint("CheckResult")
    public void post(final String url, final Map<String, String> params, final CallBack<String> callBack) {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> observableEmitter) throws Exception {
                FormBody formBody = new FormBody
                        .Builder()
                        .build();
                Request request = new Request
                        .Builder()
                        .post(formBody)//Post请求的参数传递，此处是和Get请求相比，多出的一句代码</font>
                        .url(url + getParams(params))
                        .build();
                try {
                    Response response = mOkHttpClient.newCall(request).execute();
                    observableEmitter.onNext(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    observableEmitter.onError(e);
                }
            }
        }).compose(RxSchedulers.<Response>compose())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(@NonNull Response response) throws Exception {
                        callBack.onSuccess(response.body().string());
                        response.body().close();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        callBack.onFail(throwable);
                    }
                });
    }

    /**
     * 上传用户头像
     *
     * @param url
     * @param uid
     * @param filePath
     * @param callBack
     */
    public void uploadHead(final String url, final String uid, final String filePath, final CallBack<String> callBack) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> observable) throws Exception {
                //构造请求体
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("uid", uid);

                //添加文件
                File file = new File(filePath);
                multipartBodyBuilder.addFormDataPart("file", file.getName()
                        , RequestBody.create(getFileMediaType(filePath), file));

                //发起请求
                RequestBody requestBody = multipartBodyBuilder.build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Response response = mOkHttpClient.newCall(request).execute();

                LogUtil.d("上传进度", response.body().contentLength() + "");

                //解析参数
                JSONObject responseObject = new JSONObject(response.body().string());
                if (responseObject.has("code") && responseObject.getInt("code") == 200) {
                    observable.onNext(responseObject.getJSONObject("data").getString("url"));
                } else {
                    observable.onError(new Throwable("上传头像失败：错误码不等于200"));
                }
            }
        }).compose(RxSchedulers.<String>compose())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String url) {
                        callBack.onSuccess(url);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static MediaType getFileMediaType(String filePath) {
        String[] temp = filePath.split("\\.");
        String fileType = temp[temp.length - 1].toLowerCase();
        switch (fileType) {
            case "jpg":
            case "jpeg":
                return MediaType.parse("image/jpeg");
            case "png":
                return MediaType.parse("image/png");
            case "bmp":
                return MediaType.parse("image/bmp");
            default:
                return MediaType.parse("application/octet-stream");
        }
    }

    /**
     * 讲map转化成拼接参数
     */
    private String getParams(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            builder.append("&").append(key).append("=").append(value);
        }
        return builder.toString().replaceFirst("&", "?");
    }

    /**
     * 请求解析视频清晰度的地址
     *
     * @param args  请求参数
     * @param token token
     */
    public void analysisVideoClarity(final String url, final Map<String, String> args, final String token, final CallBack<String[]> callBack) {
        Observable.create(new ObservableOnSubscribe<String[]>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String[]> observable) throws Exception {
                //获取当前时间，需要裁剪到秒
                final String temp = String.valueOf(System.currentTimeMillis());
                final String currentTime = temp.substring(0, temp.length() - 3);
                final String sign = generateSign(args, currentTime, token);

                final FormBody.Builder formBodyBuilder = new FormBody.Builder(Charset.forName("UTF-8"));
                //添加服务器时间戳
                formBodyBuilder.add("time", currentTime);
                //添加签名字符串
                formBodyBuilder.add("sign", sign);
                //添加参数
                final Iterator<String> values = args.values().iterator();
                final Iterator<String> keys = args.keySet().iterator();
                while (keys.hasNext() && values.hasNext()) {
                    try {
                        formBodyBuilder.add(keys.next().trim(), values.next().trim());
                    } catch (Exception e) {
                        //装载参数错误
                        callBack.onFail(e);
                    }
                }
                final Request request = new Request.Builder()
                        .url(url)
                        .post(formBodyBuilder.build())
                        .build();
                final Response response = mOkHttpClient.newCall(request).execute();

                //将结果转化为JSONObject
                JSONObject jsonObject = new JSONObject(response.body().string());
                //结果数组
                String[] results = null;
                if (jsonObject.has("code") && jsonObject.getInt("code") == 1) {
                    JSONObject urlObject = jsonObject.getJSONObject("result").getJSONObject("url");
                    results = new String[3];
                    results[0] = urlObject.getString("index");
                    results[1] = urlObject.getString("gqurl");
                    results[2] = urlObject.getString("cqurl");
                }
                observable.onNext(results);
            }
        }).compose(RxSchedulers.<String[]>compose())
                .subscribe(new Observer<String[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String[] strings) {
                        if (strings != null) {
                            callBack.onSuccess(strings);
                        } else {
                            callBack.onFail(new Throwable("查询不到解析地址"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 请求播放地址需要的签名参数
     *
     * @param args
     * @param currentTime 精确到秒的时间戳
     * @param token
     * @return
     */

    @NonNull
    private String generateSign(final Map<String, String> args, final String currentTime, final String token) {
        //添加所有的Key进入临时List
        final ArrayList<String> keysList = new ArrayList<>(args.size());
        keysList.addAll(args.keySet());
        //讲Key根据字符串进行升序排序
        Collections.sort(keysList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        //字符串缓冲
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String key : keysList) {
            //添加Value
            stringBuilder.append(args.get(key));
        }

        //添加时间戳
        stringBuilder.append(currentTime);

        //添加密钥
        stringBuilder.append(token);

        //得到结果
        final String result = stringBuilder.toString();
        return MD5(result);
    }
}
