package com.github.kongpf8848.tkbanner

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class TKBannerViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs){


    fun setPageChangeDuration(duration: Int) {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField[this] = TKBannerScroller(this.context, duration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}