package com.github.kongpf8848.tkbanner.transformer

import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager

class StackPageTransformer : ViewPager.PageTransformer{

    override fun transformPage(page: View, position: Float) {
        if(position>0 && position<1){
            ViewCompat.setTranslationX(page, -page.getWidth() * position)
        }
    }

}