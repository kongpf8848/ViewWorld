package com.github.kongpf8848.viewworld.activity.banner

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityBannerBinding


class BannerActivity : BaseActivity<ActivityBannerBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_banner
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.button1.setOnClickListener {
            startActivity(Banner_Simple_Activity::class.java)
        }
        binding.button2.setOnClickListener {
            startActivity(Banner_ZhiHuDaily_Activity::class.java)
        }
        binding.button3.setOnClickListener {
            startActivity(Banner_Pingwest_Activity::class.java)
        }
        binding.button4.setOnClickListener {
            startActivity(Banner_Huxiu_Activity::class.java)
        }
        binding.button5.setOnClickListener {
            startActivity(Banner_LiVideo_Activity::class.java)
        }
    }
}