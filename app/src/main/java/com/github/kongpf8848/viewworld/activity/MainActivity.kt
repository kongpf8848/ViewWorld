package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        button1.setOnClickListener {
            startActivity(CountDownActivity::class.java)
        }
        button2.setOnClickListener {
            startActivity(FontSettingActivity::class.java)
        }
        button3.setOnClickListener {
            startActivity(SmsActivity::class.java)
        }
    }
}