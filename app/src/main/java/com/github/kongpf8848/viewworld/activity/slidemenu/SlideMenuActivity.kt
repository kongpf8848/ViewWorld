package com.github.kongpf8848.viewworld.activity.slidemenu

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.activity.ArcBgActivity
import com.github.kongpf8848.viewworld.activity.VerifyCodeActivity
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.kongpf.commonhelper.ScreenHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_slide_menu.*
import kotlinx.android.synthetic.main.activity_slide_menu.button1

class SlideMenuActivity: BaseActivity(){

    override fun getLayoutId(): Int {
        return R.layout.activity_slide_menu
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        button1.setOnClickListener {
            startActivity(SlideMenu1Activity::class.java)
        }
        button2.setOnClickListener {
            startActivity(SlideMenu2Activity::class.java)
        }
    }
}