package com.github.kongpf8848.viewpagerdemo.utils

import android.graphics.*
import android.widget.ImageView
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest
import java.util.*

/**
 * 圆角
 */
class RoundCornerTransform(
    private val scaleType: ImageView.ScaleType,
    leftTop: Int,
    rightTop: Int,
    rightBottom: Int,
    leftBottom: Int
) : BitmapTransformation() {
    private var radii: FloatArray? = null

    companion object {
        private const val ID = "com.jsy.tk.library.image.transfrom.RoundCornerTransform"
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
    }


    init {
        radii = floatArrayOf(
            leftTop.toFloat(),
            leftTop.toFloat(),
            rightTop.toFloat(),
            rightTop.toFloat(),
            rightBottom.toFloat(),
            rightBottom.toFloat(),
            leftBottom.toFloat(),
            leftBottom.toFloat()
        )
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val source = when (scaleType) {
            ImageView.ScaleType.CENTER_CROP -> {
                TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
            }
            ImageView.ScaleType.FIT_CENTER -> {
                TransformationUtils.fitCenter(pool, toTransform, outWidth, outHeight)
            }
            else -> {
                toTransform
            }
        }
        return roundCorner(pool, source)
    }

    private fun roundCorner(
        pool: BitmapPool,
        source: Bitmap
    ): Bitmap {
        val width = source.width
        val height = source.height
        var bitmap: Bitmap? = pool[width, height, Bitmap.Config.ARGB_8888]
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap!!)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(
            source,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val path = Path()
        path.addRoundRect(rect, radii!!, Path.Direction.CW)
        canvas.drawPath(path, paint)
        return bitmap
    }

    override fun equals(o: Any?): Boolean {
        if (o is RoundCornerTransform) {
            return Arrays.equals(radii, o.radii)
        }
        return false
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }



}