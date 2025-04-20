package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityFontSettingBinding

class FontSettingActivity : BaseActivity<ActivityFontSettingBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_font_setting
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.fontSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                value: Int,
                bManual: Boolean
            ) {
                val size = binding.fontSeekBar.getRawTextSize(value)
                binding.tvMessage.textSize = size.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}