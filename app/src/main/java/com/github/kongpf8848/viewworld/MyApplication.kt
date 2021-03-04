package com.github.kongpf8848.viewworld

import android.app.Application
import android.util.Log
import com.github.kongpf8848.viewworld.utis.LogUtils
import com.kongpf.commonhelper.ToastHelper

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            Log.d("Crash", "uncaughtException() called with: t = [" + t + "], e = [" + e.message + "]")
        }
        LogUtils.init(BuildConfig.DEBUG)
        ToastHelper.init(this)

    }

    companion object {
        lateinit var instance: MyApplication
    }
}