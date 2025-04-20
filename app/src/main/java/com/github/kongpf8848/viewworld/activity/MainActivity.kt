package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.activity.banner.BannerActivity
import com.github.kongpf8848.viewworld.activity.slidemenu.SlideMenuActivity
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.button1.setOnClickListener {
            startActivity(ArcBgActivity::class.java)
        }
        binding.button4.setOnClickListener {
            startActivity(VerifyCodeActivity::class.java)
        }
        binding.button5.setOnClickListener {
            startActivity(VerifyCode2Activity::class.java)
        }
        binding.button6.setOnClickListener {
            startActivity(TabLayoutActivity::class.java)
        }
        binding.button7.setOnClickListener {
            startActivity(BannerActivity::class.java)
        }
        binding.button8.setOnClickListener {
            startActivity(ProgressActivity::class.java)
        }
        binding.button9.setOnClickListener {
            startActivity(TextViewActivity::class.java)
        }
        binding.button9.setOnClickListener {
            startActivity(TextViewActivity::class.java)
        }
        binding.button10.setOnClickListener {
            startActivity(SlideMenuActivity::class.java)
        }

        binding.button11.setOnClickListener {
            startActivity(SelectAvatarActivity::class.java)
        }

    }
}