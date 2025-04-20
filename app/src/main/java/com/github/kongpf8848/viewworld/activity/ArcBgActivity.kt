package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityArcBgBinding

class ArcBgActivity : BaseActivity<ActivityArcBgBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_arc_bg
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}