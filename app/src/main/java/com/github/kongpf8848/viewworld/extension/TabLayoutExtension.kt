package com.github.kongpf8848.viewworld.extension

import android.annotation.SuppressLint
import androidx.appcompat.graphics.drawable.DrawableWrapper
import com.github.kongpf8848.viewworld.views.TabLayoutEx
import com.google.android.material.tabs.TabLayout

/**
 * 设置指示符固定宽度
 */
fun TabLayout.setSelectedTabIndicatorFixWidth(width: Float) {
    setSelectedTabIndicator(@SuppressLint("RestrictedApi")
    object : DrawableWrapper(tabSelectedIndicator) {
        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            var realLeft = left
            var realRight = right
            if ((right - left).toFloat() != width) {
                val center = left + (right - left).toFloat() / 2
                realLeft = (center - width / 2).toInt()
                realRight = (center + width / 2).toInt()
            }
            super.setBounds(realLeft, top, realRight, bottom)
        }
    })
}

fun TabLayoutEx.setSelectedTabIndicatorFixWidth(width: Float) {
    setSelectedTabIndicator(@SuppressLint("RestrictedApi")
    object : DrawableWrapper(tabSelectedIndicator) {
        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            var realLeft = left
            var realRight = right
            if ((right - left).toFloat() != width) {
                val center = left + (right - left).toFloat() / 2
                realLeft = (center - width / 2).toInt()
                realRight = (center + width / 2).toInt()
            }
            super.setBounds(realLeft, top, realRight, bottom)
        }
    })
}