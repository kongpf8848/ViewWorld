package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.os.Looper
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityCountDownBinding

/**
 * 倒计时文本
 */
class CountDownActivity : BaseActivity<ActivityCountDownBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_count_down
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)

        binding.tvSkip.callback = ::onAnimationEnd
        Looper.myQueue().addIdleHandler {
            binding.tvSkip.start()
            false
        }
    }

    private fun onAnimationEnd() {
        finish()
    }

    override fun onStop() {
        super.onStop()
        binding.tvSkip.stop()
    }
}