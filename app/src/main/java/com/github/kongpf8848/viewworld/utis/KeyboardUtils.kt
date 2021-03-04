package com.github.kongpf8848.viewworld.utis

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    /**
     * 显示键盘
     * @param view
     */
    fun showSoftInput(view: View?) {
        if (view != null) {
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
            val imm = view.context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * 隐藏键盘
     * @param activity
     */
    fun hideSoftInput(activity: Activity?) {
        if (activity != null) {
            hideSoftInput(activity.currentFocus)
        }
    }

    fun hideSoftInput(view: View?) {
        if (view != null) {
            val imm = view.context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}