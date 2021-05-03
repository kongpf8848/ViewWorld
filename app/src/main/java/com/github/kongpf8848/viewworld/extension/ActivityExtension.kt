package com.github.kongpf8848.viewworld.extension

import androidx.appcompat.app.AppCompatActivity
import com.kongpf.commonhelper.ScreenHelper


fun AppCompatActivity.dp2px(dp:Float):Int{
    return ScreenHelper.dp2px(this,dp)
}
