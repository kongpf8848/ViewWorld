package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.os.Looper
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_count_down.*

/**
 * 倒计时文本
 */
class CountDownActivity:BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.activity_count_down
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)

        tv_skip.callback=::onAnimationEnd
        Looper.myQueue().addIdleHandler {
            tv_skip.start()
            false
        }
    }

    private fun onAnimationEnd(){
        finish()
    }

    override fun onStop() {
        super.onStop()
        tv_skip.stop()
    }
}