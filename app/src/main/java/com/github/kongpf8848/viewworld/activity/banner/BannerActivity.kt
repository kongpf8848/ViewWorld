package com.github.kongpf8848.viewworld.activity.banner

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_font_setting.*
import kotlinx.android.synthetic.main.activity_main.*

class BannerActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_banner
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener { finish() }
        button1.setOnClickListener {
            startActivity(Banner_Simple_Activity::class.java)
        }
        button2.setOnClickListener {
            startActivity(Banner_ZhiHuDaily_Activity::class.java)
        }
        button3.setOnClickListener {
            startActivity(Banner_Pingwest_Activity::class.java)
        }
        button4.setOnClickListener {
            startActivity(Banner_Huxiu_Activity::class.java)
        }
    }
}