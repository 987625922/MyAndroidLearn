package com.wyt.common.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by taihong on 2017/1/12.
 */

public class SDCardUtils {

    /**
     * 获取USB路径
     */
    public static String getUsbPath(Context context, boolean isCheckWritable) {
        String path = null;
        ArrayList<String> paths = getStorageData(context);
        if (paths != null && paths.size() >= 2) {
            for (String _path : paths) {
                if (!_path.equals(SDCardUtils.getLoadSDPathCheck(context)) && (_path.contains("usb") || _path.contains("USB"))) {
                    if (checkFsWritable(_path + "/")) {
                        path = _path + "/";
                    } else {
                        path = _path + "/Android/data/" + context.getPackageName() + "/";
                    }
                }
            }
        }
        if (isCheckWritable) {
            if (path != null && checkFsWritable(path)) {
            } else {
                path = null;
            }
        }
        return path;
    }


    /**
     * 获取外置TF卡路径  没有判断是否读写
     *
     * @param context
     * @return
     */
    public static String getExternalTFCardPath(Context context) {
        String path = null;
        ArrayList<String> paths = getStorageData(context);
        if (paths != null) {
            for (String _path : paths) {
                if (!_path.equals(SDCardUtils.getLoadSDPathCheck(context)) && !_path.contains("usb")) {
                    if (checkFsWritable(_path + "/")) {
                        path = _path + "/";
                    } else {
                        path = _path + "/Android/data/" + context.getPackageName() + "/";
                    }
                    break;
                }
            }
        }
        return path;
    }

    public static String getLoadSDPathCheck(Context context) {
        String path = null;
        ArrayList<String> paths = getStorageData(context);
        if (paths != null) {
            for (String _path : paths) {
                if (!_path.contains("usb")) {
                    path = _path;
                    break;
                }
            }
        }
        if (path != null && checkFsWritable(path)) {
            return path;
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
//        return path;
    }

    public static boolean checkFsWritable(String dir) {
        if (dir == null) {
            return false;
        }
        File directory = new File(dir);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        File f = new File(directory, ".test");
        try {
            if (f.exists()) {
                f.delete();
            }
            if (!f.createNewFile()) {
                return false;
            }
            f.delete();
            return true;

        } catch (Exception e) {
            //  Log.e("file", "检查外置讀寫存储卡异常" + e.getMessage());
        }
        return false;

    }

    public static ArrayList<String> getStorageData(Context pContext) {
        final StorageManager storageManager = (StorageManager) pContext.getSystemService(Context.STORAGE_SERVICE);
        try {
            final Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            final Class<?> storageValumeClazz = Class.forName("android.os.storage.StorageVolume");
            final Method getPath = storageValumeClazz.getMethod("getPath");
            final Object invokeVolumeList = getVolumeList.invoke(storageManager);
            final int length = Array.getLength(invokeVolumeList);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                final Object storageValume = Array.get(invokeVolumeList, i);
                final String path = (String) getPath.invoke(storageValume);
                list.add(path);
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getTotalSize(String path) {
        try {
            final StatFs statFs = new StatFs(path);
            long blockSize = 0;
            long blockCountLong = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                blockCountLong = statFs.getBlockCountLong();
            } else {
                blockSize = statFs.getBlockSize();
                blockCountLong = statFs.getBlockCount();
            }
            return blockSize * blockCountLong;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getAvailableSize(String path) {
        try {
            final StatFs statFs = new StatFs(path);
            long blockSize = 0;
            long availableBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            } else {
                blockSize = statFs.getBlockSize();
                availableBlocks = statFs.getAvailableBlocks();
            }
            return availableBlocks * blockSize;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
