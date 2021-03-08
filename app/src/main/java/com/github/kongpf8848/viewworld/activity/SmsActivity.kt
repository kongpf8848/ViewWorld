package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.utis.KeyboardUtils
import com.github.kongpf8848.viewworld.utis.MainHandlerUtils
import com.github.kongpf8848.viewworld.views.CodeEditText
import com.kongpf.commonhelper.ToastHelper
import kotlinx.android.synthetic.main.activity_font_setting.toolbar
import kotlinx.android.synthetic.main.activity_sms_code.*

/**
 * 验证码
 */
class SmsActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_sms_code
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener { finish() }

        et_sms_code.setOnTextFinishListener(object : CodeEditText.OnTextFinishListener {
            override fun onTextFinish(text: CharSequence) {
                ToastHelper.toastCenter("你输入的验证码为:${text}")
            }

        })

        Looper.myQueue().addIdleHandler {
            MainHandlerUtils.postDelayed({
                KeyboardUtils.showSoftInput(et_sms_code)
            }, 200)

            false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtils.hideSoftInput(this)
    }
}