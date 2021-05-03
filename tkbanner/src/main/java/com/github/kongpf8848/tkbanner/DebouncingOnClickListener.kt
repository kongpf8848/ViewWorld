package com.github.kongpf8848.tkbanner

import android.view.View

abstract class DebouncingOnClickListener : View.OnClickListener {

    private var clickLastTime=0L

    companion object {
        const val MIN_DURATION=500L
    }

    override fun onClick(v: View) {
        val currentTime=System.currentTimeMillis()
        if(currentTime-clickLastTime>MIN_DURATION){
           doClick(v)
       }
        clickLastTime=currentTime
    }


    abstract fun doClick(v: View)
}