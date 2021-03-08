package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import com.github.kongpf8848.viewworld.R


class AvatarView(context: Context, attributeSet: AttributeSet): View(context,attributeSet) {

    private val IMAGE_WIDTH = 300f
    private val IMAGE_PADDING = 100f
    private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG);
    private val bounds = RectF(IMAGE_PADDING,IMAGE_PADDING,IMAGE_PADDING+ IMAGE_WIDTH,IMAGE_PADDING+IMAGE_WIDTH)

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bounds,null);
        canvas.drawOval(IMAGE_PADDING,IMAGE_PADDING,IMAGE_PADDING+ IMAGE_WIDTH,IMAGE_PADDING+IMAGE_WIDTH,paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()),IMAGE_PADDING,IMAGE_PADDING,paint)
        paint.xfermode = null
        canvas.restoreToCount(count)

    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.girl, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.girl, options)
    }

}
