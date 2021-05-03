package com.github.kongpf8848.tkbanner

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes

object TKBannerUtils {

    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            context.resources.displayMetrics
        ).toInt()
    }

    fun getItemImageView(
        context: Context,
        @DrawableRes resId: Int,
        scaleType: ScaleType = ScaleType.CENTER_CROP
    ): ImageView {
        return ImageView(context).apply {
            setImageResource(resId)
            isClickable = true
            setScaleType(scaleType)
        }
    }

    fun resetPageTransformer(views: List<View>?) {
        views?.forEach {view ->
            view.visibility = View.VISIBLE
            view.alpha=1.0f
            view.pivotX=view.measuredWidth.toFloat() * 0.5f
            view.pivotY=view.measuredHeight.toFloat()*0.5f
            view.translationX=0.0f
            view.translationY=0.0f
            view.scaleX=1.0f
            view.scaleY=1.0f
            view.rotationX=0.0f
            view.rotationY=0.0f
            view.rotation=0.0f
        }
    }

}