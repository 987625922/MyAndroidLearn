package com.wyt.common.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Base64
import android.view.View
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @Author: LL
 * @Description: bitmap工具类
 * @Date:Create：in 2020/7/7 11:18
 */
object BitmapUtil {
    /**
     * 获取本地图片转成bitmap
     *
     * @param path
     * @return
     */
    fun get(path: String): Bitmap? {
        val bitmap = BitmapFactory.decodeFile(path)
        Log.d("BitmapUtil 获取图片的路径：$path")
        if (bitmap == null) {
            Log.d("BitmapUtil 获取图片为空")
        }
        return bitmap
    }

    /**
     * 在图片又边添加一个一样的白色bitmap图层
     */
    fun addWithBitmaps(bitmap: Bitmap): Bitmap? {
        var width = bitmap.width * 2
        var height = bitmap.height
        //为节省内存把ARGB_8888改成RGB_565
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(result)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(
            bitmap, 0f, 0f,
            null
        )
        val logWidth = result.width
        val logHeight = result.height
        Log.d("BitmapUtil 生成的bitmap大小 宽：$logWidth 高：$logHeight")
        return result
    }

    /**
     * 多个bitmap合成一个
     *
     * @param bitmaps
     * @return
     */
    fun addBitmaps(vararg bitmaps: Bitmap?): Bitmap? {
        var width = 0
        var height = 0
        val leng = bitmaps.size
        for (i in 0 until leng) {
            if (bitmaps[i] == null) {
                return null
            }
            width += bitmaps[i]!!.width
            height = Math.max(height, bitmaps[i]!!.height)
        }
        //为节省内存把ARGB_8888改成RGB_565
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(result)
        var left = 0
        for (i in 0 until leng) {
            if (i > 0) {
                left += bitmaps[i - 1]!!.width
            }
            //合成bitmap，left 为bitmap离左边的距离 top 为离上面的距离
            canvas.drawBitmap(
                bitmaps[i]!!, left.toFloat(), 0f,
                null
            )
        }
        val logWidth = result.width
        val logHeight = result.height
        Log.d("BitmapUtil 生成的bitmap大小 宽：$logWidth 高：$logHeight")
        return result
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap Bitmap
     */
    fun saveBitmap(bitmap: Bitmap, path: String) {
        val filePic: File
        val savePath: String = if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED
            )
        ) {
            path
        } else {
            Log.d("saveBitmap failure : sdcard not mounted")
            return
        }
        try {
            filePic = File(savePath)
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs()
                filePic.createNewFile()
            }
            val fos = FileOutputStream(filePic)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            Log.i("saveBitmap: " + e.message)
            return
        }
        Log.i("saveBitmap success: " + filePic.getAbsolutePath())
    }

    /**
     * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
     * @param backBitmap 在底部的位图
     * @param frontBitmap 盖在上面的位图
     * @return
     */
    fun mergeBitmap(backBitmap: Bitmap?, frontBitmap: Bitmap?): Bitmap? {
        if (backBitmap == null || backBitmap.isRecycled
            || frontBitmap == null || frontBitmap.isRecycled
        ) {
            return null
        }
        val bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(bitmap)
        val baseRect = Rect(0, 0, backBitmap.width, backBitmap.height)
        val frontRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null)
        return bitmap
    }

    /**
     * 获取bitmap左一半的bitmap
     */
    fun cropLeftBitmap(bitmap: Bitmap): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val cropWidth = w / 2
        return Bitmap.createBitmap(bitmap, 0, 0, cropWidth, h, null, false)
    }


    /**
     * 获取bitmap右一半的bitmap
     */
    fun cropRightBitmap(bitmap: Bitmap): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val cropWidth = w / 2
        return Bitmap.createBitmap(bitmap, cropWidth, 0, cropWidth, h, null, false)
    }

    /**
     * 通过view获取bitmap
     */
    fun loadBitmapFromView(v: View): Bitmap {
        val w: Int = v.getWidth()
        val h: Int = v.getHeight()
        val bmp =
            Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bmp.setHasAlpha(true)
        val c = Canvas(bmp)
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
//        c.drawColor(Color.WHITE)
//        c.drawColor(Color.parseColor("#00000000"))
        /** 如果不设置canvas画布为白色，则生成透明  */
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }

    /**
     * bitmap转base64
     */
    fun bitmapToBase64(bitmap: Bitmap?): String? {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                baos.flush()
                baos.close()
                val bitmapBytes: ByteArray = baos.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    /**
     * base64转图片
     */
//    suspend fun baseToBitmap(value: String?): Bitmap = withContext(Dispatchers.IO) {
//        val decodedString: ByteArray = if (value?.contains(",")!!) {
//            Base64.decode(
//                value.split(",")[1],
//                Base64.DEFAULT
//            )
//        } else {
//            Base64.decode(
//                value,
//                Base64.DEFAULT
//            )
//        }
//
//        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//    }

    /**
     * drawable转图片
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        val bitmap: Bitmap
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1, 1,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 压缩bitmap大小
     */
    fun decodeSampledBitmapFromBitmap(bitmap: Bitmap, newHeight: Int): Bitmap {
        // 获得图片的宽高
        val width: Int = bitmap.width
        val height: Int = bitmap.height
        // 计算缩放比例
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        //宽高都是按照高度压缩的比例来压缩
        matrix.postScale(scaleHeight, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }
}