package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.kongpf.commonhelper.ScreenHelper
import kotlinx.android.synthetic.main.activity_font_setting.*
import kotlinx.android.synthetic.main.activity_font_setting.toolbar
import kotlinx.android.synthetic.main.activity_textview.*

class TextViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_textview
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}