package com.github.kongpf8848.tkbanner

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

class TKBannerScroller @JvmOverloads constructor(
    private val context: Context,
    private val scrollDuration: Int=1000,
    private val interpolator: Interpolator? = null,
    private val flywheel: Boolean=true
) : Scroller(context,interpolator,flywheel) {

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, scrollDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, scrollDuration)
    }
}
