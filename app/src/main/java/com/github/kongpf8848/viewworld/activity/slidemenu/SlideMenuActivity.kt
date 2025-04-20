package com.github.kongpf8848.viewworld.activity.slidemenu

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivitySlideMenuBinding

class SlideMenuActivity : BaseActivity<ActivitySlideMenuBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_slide_menu
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.button1.setOnClickListener {
            startActivity(SlideMenu1Activity::class.java)
        }
        binding.button2.setOnClickListener {
            startActivity(SlideMenu2Activity::class.java)
        }
    }
}