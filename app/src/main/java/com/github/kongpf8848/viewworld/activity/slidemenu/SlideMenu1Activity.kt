package com.github.kongpf8848.viewworld.activity.slidemenu

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivitySlideMenu1Binding
import io.github.kongpf8848.commonhelper.ScreenHelper

class SlideMenu1Activity : BaseActivity<ActivitySlideMenu1Binding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_slide_menu_1
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.slideMenu.setMenuWidth(ScreenHelper.dp2px(applicationContext, 180f))
    }
}