package com.github.kongpf8848.viewworld.utis

import android.os.Handler
import android.os.Looper

/**
 * Handler帮助类
 */
object MainHandlerUtils : Handler(Looper.getMainLooper()) {

    fun runOnUiThread(runnable: Runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run()
        } else {
            post(runnable)
        }
    }
}