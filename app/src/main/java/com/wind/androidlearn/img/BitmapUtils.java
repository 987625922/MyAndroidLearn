package com.wind.androidlearn.img;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建日期：2018/9/11 0011<br>
 * 作 者：蒋委员长<br>
 * 功 能：<br>
 * 注 意：<br>
 * 待做事情：
 */
public class BitmapUtils {

    public static void getByteBitmap(Bitmap bitmap) {
        Log.e("BitmapUtils", "大小：" + bitmap.getByteCount() / 1024 + " k");
    }

    /**
     * 采样率压缩
     *
     * @param res
     * @param id
     * @param opts
     * @return
     */
    private Bitmap decodeBitmapFromResource(Resources res, int id, BitmapFactory.Options opts) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        options.inSampleSize = calculateSampleSize(options, 300, 300);
        options.inJustDecodeBounds = false;
        try {
            // 实例化Bitmap
            return BitmapFactory.decodeResource(res, id, options);

        } catch (OutOfMemoryError e) {
            Log.e("BitmapUtils", "内存溢出");
        }
        return null;
    }


    /**
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return 计算合适的采样率(当然这里还可以自己定义计算规则)，reqWidth为期望的图片大小，单位是px
     */
    private int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        Log.i("========", "calculateSampleSize reqWidth:" +
                reqWidth + ",reqHeight:" + reqHeight);
        int width = options.outWidth;
        int height = options.outHeight;
        Log.i("========", "calculateSampleSize width:" + width + ",height:" + height);
        int inSampleSize = 1;
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
            inSampleSize *= 2;
            Log.i("========", "calculateSampleSize inSampleSize:" + inSampleSize);
        }
        return inSampleSize;
    }

    /**
     * 质量压缩
     *
     * @param image
     * @param reqSize 经过它压缩的图片文件大小(kb)会有改变，但是导入成bitmap后占得内存是不变的，宽高也不会改变
     */
    private void compressImage(Bitmap image, int reqSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100,
                baos);// 质量压缩方法，这里100表示不压缩，
        int options = 100;
        while (baos.toByteArray().length / 1024 > reqSize) {
            // 循环判断压缩后的图片是否大于reqSize，大于则继续压缩
            baos.reset();//清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options,
                    baos);// 这里压缩options%，把压缩后的数据放到baos中
            options -= 10;
        }
        // 把压缩后的baos放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //decode图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
    }

    // 图片旋转指定角度
    private Bitmap rotateImage(Bitmap image, final int degree) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > height) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            if (image != null && !image.isRecycled()) {
                Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0,
                        width, height, matrix, true);
                return resizedBitmap;
            } else {
                return null;
            }
        } else {
            return image;
        }
    }

    /**
     * 图片圆角
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap roundCornerBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);
        int color = 0xff424242;// int color = 0xff424242;
        Paint paint = new Paint();
        paint.setColor(color);
        // 防止锯齿
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float roundPx = pixels;
        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        // 先画了一个带圆角的矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 再把原来的bitmap画到现在的bitmap！！！注意这个理解
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return roundCornerBitmap;
    }

    /**
     * drawable转换成Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
                        Bitmap.Config.RGB_565
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 图片保存到sd
     *
     * @param bitmap
     * @param path
     */
    public void savePic(Bitmap bitmap,String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
