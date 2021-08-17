package com.github.kongpf8848.viewworld.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Looper
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_progress.*


class ProgressActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_progress
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener { finish() }

        btn_play.setOnClickListener {
            val animator=ValueAnimator.ofInt(0,100).apply {
                duration=3000
                addUpdateListener { animation ->
                    audio_progress.onUpdateProgress((animation.animatedValue as Int).toLong(), 100L)
                }
            }
            animator.start()
        }
        btn_reset.setOnClickListener {
            audio_progress.onResetProgress()
        }
        Looper.myQueue().addIdleHandler {
            audio_progress.setLevels(levels)
            false
        }
    }

    companion object{
        val levels=floatArrayOf(
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.7f,0.02f,0.3f,0.2f,0.1f,
            0.6f,0.02f,0.3f,0.2f,0.1f,
            0.5f,0.02f,0.34f,0.2f,0.6f,
            0.01f,0.02f,0.11f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.31f,
            0.01f,0.02f,0.6f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.021f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.41f,
            0.051f,0.02f,0.3f,0.51f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.2f,0.2f,0.71f,
            0.081f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.46f,0.1f,
            0.01f,0.02f,0.3f,0.56f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f,
            0.01f,0.02f,0.3f,0.2f,0.1f
        )
    }
}