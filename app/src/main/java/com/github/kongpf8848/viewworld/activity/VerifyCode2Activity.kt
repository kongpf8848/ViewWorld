package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.os.Looper
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityVerifyCodeSquareBinding
import com.github.kongpf8848.viewworld.utis.KeyboardUtils
import com.github.kongpf8848.viewworld.utis.MainHandlerUtils
import com.github.kongpf8848.viewworld.views.VerificationCodeEditText
import io.github.kongpf8848.commonhelper.ToastHelper


/**
 * 验证码输入框
 */
class VerifyCode2Activity : BaseActivity<ActivityVerifyCodeSquareBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_verify_code_square
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.etVerificationCode.setOnInputTextListener(object :
            VerificationCodeEditText.OnInputTextListener {
            override fun onInputTextComplete(text: CharSequence) {
                ToastHelper.toastCenter("你输入的验证码为:${text}")
            }
        })

        Looper.myQueue().addIdleHandler {
            MainHandlerUtils.postDelayed({
                KeyboardUtils.showSoftInput(binding.etVerificationCode)
            }, 500)

            false
        }

    }

    override fun finish() {
        KeyboardUtils.hideSoftInput(this)
        super.finish()
    }
}