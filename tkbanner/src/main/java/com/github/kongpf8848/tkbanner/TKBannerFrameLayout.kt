package com.github.kongpf8848.tkbanner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt

class TKBannerFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

    fun getBannerRelativeLayout()=findViewById<RelativeLayout>(R.id.banner_item)
    fun getBannerImageView()=findViewById<ImageView>(R.id.banner_item_image)
    fun getBannerTextView()=findViewById<TextView>(R.id.banner_item_text)

    fun setBannerMargin(margin:Int){
        setBannerLeftRightMargin(margin)
        setBannerTopBottomMargin(margin)
    }

    fun setBannerLeftMargin(margin:Int){
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).leftMargin=margin
    }
    fun setBannerRightMargin(margin:Int){
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).rightMargin=margin
    }
    fun setBannerTopMargin(margin:Int){
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).topMargin=margin
    }
    fun setBannerBottomMargin(margin:Int){
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).bottomMargin=margin
    }

    fun setBannerLeftRightMargin(margin:Int){
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).leftMargin=margin
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).rightMargin=margin
    }

    fun setBannerTopBottomMargin(margin:Int){
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).topMargin=margin
        (getBannerRelativeLayout().layoutParams as FrameLayout.LayoutParams).bottomMargin=margin
    }

    fun setBannerImageResource(resId:Int){
        getBannerImageView().setImageResource(resId)
    }

    fun setBannerText(text:CharSequence?){
        getBannerTextView().text = text
    }
    fun setBannerTextColor(@ColorInt color:Int){
        getBannerTextView().setTextColor(color)
    }
    fun setBannerTextSize(size:Float){
        getBannerTextView().textSize=size
    }

}