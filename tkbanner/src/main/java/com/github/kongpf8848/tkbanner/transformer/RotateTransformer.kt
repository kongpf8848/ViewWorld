package com.github.kongpf8848.tkbanner.transformer

import android.view.View
import androidx.viewpager.widget.ViewPager

class RotateTransformer(val angle:Float = 30f) : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {

        when {
            position < -1.0f -> {
                page.rotation = -1.0f * angle
                page.pivotX = page.width.toFloat()
                page.pivotY = page.height.toFloat()
            }
            position <= 1.0f -> {
                if (position < 0.0f) {
                    page.pivotX = page.width * (0.5f + 0.5f * -position)
                    page.pivotY = page.height.toFloat()
                    page.rotation = position * angle
                } else {
                    page.pivotX = 0.5f * page.width * (1.0f - position)
                    page.pivotY = page.height.toFloat()
                    page.rotation = position * angle
                }
            }
            else -> {
                page.rotation = angle
                page.pivotX = 0f
                page.pivotY = page.height.toFloat()
            }
        }
    }

}