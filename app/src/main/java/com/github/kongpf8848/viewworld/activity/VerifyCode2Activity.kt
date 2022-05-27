package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import androidx.core.widget.doAfterTextChanged
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.utis.KeyboardUtils
import com.github.kongpf8848.viewworld.utis.MainHandlerUtils
import com.github.kongpf8848.viewworld.views.VerificationCodeEditText
import com.kongpf.commonhelper.ToastHelper
import kotlinx.android.synthetic.main.activity_font_setting.toolbar
import kotlinx.android.synthetic.main.activity_verify_code_line.*

/**
 * 验证码输入框
 */
class VerifyCode2Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_verify_code_square
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener { finish() }

        et_verification_code.setOnInputTextListener(object: VerificationCodeEditText.OnInputTextListener{
            override fun onInputTextComplete(text: CharSequence) {
                ToastHelper.toastCenter("你输入的验证码为:${text}")
            }
        })

        Looper.myQueue().addIdleHandler {
            MainHandlerUtils.postDelayed({
                KeyboardUtils.showSoftInput(et_verification_code)
            }, 500)

            false
        }

    }

    override fun finish() {
        KeyboardUtils.hideSoftInput(this)
        super.finish()
    }
}