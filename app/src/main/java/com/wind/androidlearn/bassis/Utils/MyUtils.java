package com.wind.androidlearn.bassis.Utils;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/8.
 * 工具类
 */
public class MyUtils {

    private static long lastClickTime;

    /**
     * 功能：<br>
     * 时间：2014年9月15日 上午10:45:20<br>
     * 注意：<br>
     *
     * @return boolean
     */
    public static boolean isFastDoubleClick() {

        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return false;
        } else {
            lastClickTime = time;
            return true;
        }
    }

    /**
     * @param pkg
     * @return
     */
    public static boolean launcher(Context context, String pkg) {
        boolean la = true;
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            la = false;
        }
        return la;
    }


    /**
     * 启动相机
     **/
    public static void launchCamera(Context mContext) {
        try {
            //获取相机包名
            Intent infoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ResolveInfo res = mContext.getPackageManager().resolveActivity(infoIntent, 0);
            if (res != null) {
                String packageName = res.activityInfo.packageName;
                if (packageName.equals("android")) {
                    infoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                    res = mContext.getPackageManager().resolveActivity(infoIntent, 0);
                    if (res != null)
                        packageName = res.activityInfo.packageName;
                }
                //启动相机
                startApplicationByPackageName(mContext, packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //通过包名启动应用
    private static void startApplicationByPackageName(Context mContext, String packName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(packName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == packageInfo) {
            return;
        }
        Intent resolveIntent = new Intent();
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        List<ResolveInfo> resolveInfoList = mContext.getPackageManager().queryIntentActivities(resolveIntent, 0);
        if (null == resolveInfoList) {
            return;
        }
        Iterator<ResolveInfo> iter = resolveInfoList.iterator();
        while (iter.hasNext()) {
            ResolveInfo resolveInfo = iter.next();
            if (null == resolveInfo) {
                return;
            }
            String packageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            mContext.startActivity(intent);
        }
    }

    /**
     * 通过包名启动应用
     * @param mContext
     * @param packName
     */
    public static boolean startPackageName(Context mContext, String packName) {
        /**包管理器*/
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent();
        /**获得Intent*/
        intent = packageManager.getLaunchIntentForPackage(packName);
        if (intent != null) {
            mContext.startActivity(intent);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles) || mobiles.length() != 11) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 获得当前系统前七天的日期
     *
     * @return static
     */
    public static String getWeekdaySystemTime() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        date = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String currentDate = sdf.format(date);
        return currentDate;

    }


    public static void startPhotoZoom(Context context, Uri uri, int type) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent, type);
    }

    public static Bitmap ImageCompressL2(Bitmap bitmap) {
        double targetwidth = Math.sqrt(2048.00 * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }


    /**
     * 转换时间格式
     *
     * @param date
     * @return
     */
    public static String getTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 按字典排序 ,获取大麦字符串
     *
     * @param map
     */
    public static String putParams(Map<String, String> map) {
        String sign = "";
        if (map != null) {
            map = sortMapByKey(map);
            Collection<String> keysSet = map.keySet();
            List list = new ArrayList<>(keysSet);
            Collections.sort(list);
            //生成sign
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sign += list.get(i) + "=" + map.get(list.get(i));
                } else {
                    sign += "&" + list.get(i) + "=" + map.get(list.get(i));
                }
            }
        }
        return sign;
    }
//    /**
//     * 遍历map
//     * @param map
//     * @param postFormBuilder
//     * @param time
//     */
//    public static void putParams(Map<String, String> map,
//                                 PostFormBuilder postFormBuilder,
//                                 String time) {
//        String sign="";
//        if(map !=null){
//            map=sortMapByKey(map);
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                sign+=entry.getValue();
//            }
//            //遍历添加数据
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                postFormBuilder.addParams(entry.getKey(), entry.getValue());
//                //Log.i(ConfigInfo.TAG,entry.getKey()+"="+entry.getValue());
//            }
//        }
//        postFormBuilder.addParams("time",time);
//        postFormBuilder.addParams("sign", Md5Util.md5Encode(sign+time+ConfigInfo.TOKEN_RL));
//    }

    //比较器类
    public static class MapKeyComparator implements Comparator<String> {
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
	    return isNum.matches();
    }


    /**
     * 超过1000千的转化为千为单位
     *
     * @param number 数字
     * @return String
     */
    public static String numberConversion(int number) {
        if (number > 1000) {
            double num = 0;
            num = number * 1.0 / 1000;
            return String.format("%.2f ", num) + "千";
        } else {
            return number + "";
        }
    }

    /**
     * 格灵TV字符串截取
     *
     * @param str 字符串
     * @return true or false
     */
    public static String subString(String str) {
        if (str.contains("：")) {
            str = str.substring(str.indexOf("：") + 1, str.length());
            if (str.contains("(")) {
                str = str.substring(0, str.indexOf("("));
            } else if (str.contains("（")) {
                str = str.substring(0, str.indexOf("（"));
            }
        } else {
            if (str.contains("(")) {
                str = str.substring(0, str.indexOf("("));
            } else if (str.contains("（")) {
                str = str.substring(0, str.indexOf("（"));
            } else {
                str = str.substring(str.length() > 3 ? str.length() - 3 : 0, str.length());
            }
        }
        return str;
    }

    /**
     * 格灵TV字符串截取
     *
     * @param str 字符串
     * @return true or false
     */
    public static String replaceStr(String str) {
        str = str.replaceAll("\\(全易通\\)", "").replaceAll("\\(HC\\)", "");
        str = str.replaceAll("\\（全易通\\）", "").replaceAll("\\（HC\\）", "");
        return str;
    }

    /**
     * 判断是否支持硬解码
     *
     * @return true or false
     */
    public static boolean isSupportMediaCodecHardDecoder() {
        boolean isHardcode = false;
        //读取系统配置文件/system/etc/media_codecc.xml
        File file = new File("/system/etc/media_codecs.xml");
        InputStream inFile = null;
        try {
            inFile = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (inFile != null) {
            XmlPullParserFactory pullFactory;
            try {
                pullFactory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = pullFactory.newPullParser();
                xmlPullParser.setInput(inFile, "UTF-8");
                int eventType = xmlPullParser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = xmlPullParser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if ("MediaCodec".equals(tagName)) {
                                String componentName = xmlPullParser.getAttributeValue(0);
                                if (componentName.startsWith("OMX.")) {
                                    if (!componentName.startsWith("OMX.google.")) {
                                        isHardcode = true;
                                    }
                                }
                            }
                    }
                    eventType = xmlPullParser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isHardcode;
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime   现在的时间
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return true or false
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
	    return date.after( begin ) && date.before( end );
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime 现在的时间
     * @param endTime 结束时间
     * @return true or false
     */
    public static boolean belongCalendar(Date nowTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
	    return date.before( end );
    }

}
