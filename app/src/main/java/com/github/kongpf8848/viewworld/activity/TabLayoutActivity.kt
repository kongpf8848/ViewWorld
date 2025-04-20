package com.github.kongpf8848.viewworld.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.kongpf8848.androidworld.adapter.FragmentAdapter
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityTabLayoutBinding
import com.github.kongpf8848.viewworld.fragment.TitleFragment


class TabLayoutActivity : BaseActivity<ActivityTabLayoutBinding>() {

    private val titlesList = listOf("Microsoft", "Goolge", "Apple", "Samsung", "Facebook", "Amazon")

    override fun getLayoutId(): Int {
        return R.layout.activity_tab_layout
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        val fragments = ArrayList<Fragment>()
        titlesList.forEach {
            fragments.add(TitleFragment.newInstance(it))
        }

        val adapter = FragmentAdapter(supportFragmentManager, fragments, titlesList)
        binding.viewPager.adapter = adapter

        binding.tabLayoutOrigin.setupWithViewPager(binding.viewPager)

        binding.tabLayout1.setupWithViewPager(binding.viewPager)
        binding.tabLayout2.setupWithViewPager(binding.viewPager)
        binding.tabLayout3.setupWithViewPager(binding.viewPager)

    }
}