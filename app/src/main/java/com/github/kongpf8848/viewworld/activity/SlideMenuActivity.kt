package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.kongpf.commonhelper.ScreenHelper
import kotlinx.android.synthetic.main.activity_slide_menu.*

class SlideMenuActivity: BaseActivity(){

    override fun getLayoutId(): Int {
        return R.layout.activity_slide_menu
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        slideMenu.setMenuWidth(ScreenHelper.dp2px(applicationContext,180f))
    }
}