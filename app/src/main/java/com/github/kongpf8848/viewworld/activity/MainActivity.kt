package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.activity.banner.BannerActivity
import com.github.kongpf8848.viewworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        button1.setOnClickListener {
            startActivity(ArcBgActivity::class.java)
        }
        button4.setOnClickListener {
            startActivity(VerifyCodeActivity::class.java)
        }
        button5.setOnClickListener {
            startActivity(VerifyCode2Activity::class.java)
        }
        button6.setOnClickListener {
            startActivity(TabLayoutActivity::class.java)
        }
        button7.setOnClickListener {
            startActivity(BannerActivity::class.java)
        }
        button8.setOnClickListener {
            startActivity(ProgressActivity::class.java)
        }
        button9.setOnClickListener {
            startActivity(TextViewActivity::class.java)
        }
    }
}