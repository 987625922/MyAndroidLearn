package com.wind.wind.androidlearn.bassis.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/11.
 * 获取手机信息
 */
public class PhoneUtils {
    /**
     * User-Agent
     *
     * @return user-agent
     */
    public static String getUser_Agent() {
        String ua = "Android;" + getOSVersion() + ";" + getVersion(null) + ";"
                + getVendor() + "-" + getDevice();

        return ua;
    }

    /**
     * device model name, e.g: GT-I9100
     *
     * @return the user_Agent
     */
    public static String getDevice() {
        return Build.MODEL;
    }


    /**
     * device factory name, e.g: Samsung
     *
     * @return the vENDOR
     */
    public static String getVendor() {
        return Build.BRAND;
    }


    /**
     * @return the SDK version
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * @return the OS version
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * Retrieves application's version number from the manifest
     *
     * @return versionName
     */
    public static String getVersion(Context mContent) {
        String version = "0.0.0";
        try {
            PackageInfo packageInfo = mContent.getPackageManager().getPackageInfo(
                    mContent.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 是否具备电话功能判断方法
     *
     * @param activity
     * @return
     */
    public static boolean isTelephony(Activity activity) {
        TelephonyManager telephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        return telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取SIM序列号
     *
     * @param activity
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getSerialNumberSIM(Activity activity) {
        TelephonyManager tManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getSimSerialNumber();
    }

    /**
     * @description：获取设备唯一标识
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceUni(Context context) {
        String deviceUni = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceUni = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceUni)) {
            deviceUni = getMacAddress(context);
        }
        return deviceUni;
    }

    /**
     * @description：获取wifimac地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        return wifiMac;
    }


    /**
     * 启动一个activity
     */
    public static void startActivity(Context context, Intent intent) {
        try {
            context.startActivity(intent);
//            ((Activity) context).overridePendingTransition(R.anim.activity_open, R.anim.activity_close1);
        } catch (Exception ignore) {
        }
    }

    public static void startActivity(Context context, Intent intent, int p) {
        try {
            ((Activity) context).startActivityForResult(intent, p);
//            ((Activity) context).overridePendingTransition(R.anim.activity_open, R.anim.activity_close1);
        } catch (Exception e) {
            ToastUtils.showShort(context, "没有找到该页面");
        }
    }

    /**
     * 通过包名来打开一个APP
     *
     * @param context     可用的上下文对象
     * @param packageName 要打开的APP包名
     */
    public static void startAppByPackageName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setPackage(packageInfo.packageName);

            List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
            if (resolveInfos.size() == 0) {
                throw new Exception();
            }

            intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName(resolveInfos.get(0).activityInfo.packageName, resolveInfos.get(0).activityInfo.name));
//            intent.putExtra("code", MD5Utils.encrypt32(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(new Date(System.currentTimeMillis()))));
            startActivity(context.getApplicationContext(), intent);
        } catch (Exception ignore) {
        }
    }

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * 验证用户名，如6到12位字母数字组合
     *
     * @param username 用户名
     * @return boolean
     */
    public static boolean checkUsername(String username) {
        String regex = "([a-zA-Z0-9]{6,12})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }


    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }


    /**
     * 检查是否存在SDCard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 设置该属性可获得图片的长宽等信息，但是避免了不必要的提前加载动画
        opt.inJustDecodeBounds = false;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public String installApk(String apkPath) {

        return "";
    }


    /**
     * 判读url是否可用
     *
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setConnectTimeout(100);
            int code = conn.getResponseCode();
            if (conn != null) {
                conn.disconnect();
            }
            value = code == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }


    /**
     * 判断文件是否可用
     *
     * @param url
     * @return
     */
    public static boolean checkURLSize(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(10000);
            // 设置通用的请求属性
            conn.setRequestProperty("Accept-Encoding", "identity");
            conn.setRequestProperty("Connection", "close");
            conn.setRequestMethod("POST");
            int fileSize = conn.getContentLength();
            //Log.i(ConfigInfo.TAG,"checkURLSize fileSize="+fileSize);
            if (conn != null) {
                conn.disconnect();
            }
            value = fileSize > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Is the live streaming still available
     *
     * @return is the live streaming is available
     */
    public static boolean isLiveStreamingAvailable() {
        // Todo: Please ask your app server, is the live streaming still available
        return true;
    }

    /**
     * 判断是否运行在模拟器上
     *
     * @param context
     * @return
     */
    public static boolean isEmulatorByImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        Log.e("imei=", "imei=" + imei);
        return imei == null || imei.equals("000000000000000");
    }

    /**
     * 系统内存信息文件
     *
     * @param context
     * @return
     */
    private String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 系统当前可用内存
     *
     * @param context
     * @return
     */
    private String getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }
}
