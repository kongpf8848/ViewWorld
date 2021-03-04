package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import kotlinx.android.synthetic.main.activity_font_setting.*

class FontSettingActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_font_setting
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        font_seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                value: Int,
                bManual: Boolean
            ) {
                val size= font_seekBar.getRawTextSize(value)
                tv_message.textSize = size.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}