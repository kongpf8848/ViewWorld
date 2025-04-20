package com.github.kongpf8848.viewworld.activity.banner

import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.github.kongpf8848.tkbanner.TKBanner
import com.github.kongpf8848.tkbanner.TKBannerFrameLayout
import com.github.kongpf8848.tkbanner.transformer.StackPageTransformer
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivityBannerSimpleBinding
import com.github.kongpf8848.viewworld.extension.dp2px
import com.github.kongpf8848.viewworld.model.BannerItem
import com.github.kongpf8848.viewworld.utis.ImageLoader
import com.github.kongpf8848.viewworld.utis.LogUtils

class Banner_Simple_Activity : BaseActivity<ActivityBannerSimpleBinding>() {

    val bannerList = listOf(
        BannerItem(
            id = 1,
            url = "http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853",
            title = "Redmi K40游戏增强版：不是传统意义上的游戏手机"
        ),
        BannerItem(
            id = 2,
            url = "https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a6efce1b9d16fdfabf36882ab08f8c5495ee7b9f.jpg",
            title = "马斯克的妈妈入驻小红书了，你猜是不是来给儿子PR的？"
        ),
        BannerItem(
            id = 3,
            url = "https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/0824ab18972bd40797d8db1179899e510fb3093a.jpg",
            title = "TikTok空降华人CEO：年仅30多岁，曾就职脸书，被雷军戏称“第二帅男神”"
        ),
        BannerItem(
            id = 4,
            url = "http://t8.baidu.com/it/u=198337120,441348595&fm=79&app=86&f=JPEG?w=1280&h=732",
            title = "很可能是3年后人类唯一在运行的空间站，由中国开始搭建"
        )
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_banner_simple
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { finish() }

        listOf(binding.banner1, binding.banner2).forEach {
            it.apply {
                setAutoPlayInterval(3000)
                setAutoPlayAble(false)
                setPageTransformer(true, StackPageTransformer())
                setData(models = bannerList)
                setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                        LogUtils.d(TAG, "onPageScrollStateChanged() called with: state = $state")
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        LogUtils.d(
                            TAG,
                            "onPageScrolled() called with: position = $position, positionOffset = $positionOffset, positionOffsetPixels = $positionOffsetPixels"
                        )
                    }

                    override fun onPageSelected(position: Int) {
                        LogUtils.d(TAG, "onPageSelected() called with: position = $position")
                    }

                })
                setOnBannerItemClickListener(object :
                    TKBanner.OnBannerItemClickListener<BannerItem> {
                    override fun onBannerItemClick(
                        banner: TKBanner,
                        model: BannerItem,
                        position: Int
                    ) {
                        LogUtils.d(
                            TAG,
                            "onBannerItemClick() called with: banner = $banner, model = $model, position = $position"
                        )
                    }
                })
                setAdapter(object : TKBanner.Adapter<BannerItem> {
                    override fun fillBannerItem(
                        banner: TKBanner,
                        view: View,
                        model: BannerItem,
                        position: Int
                    ) {
                        LogUtils.d(
                            TAG,
                            "fillBannerItem() called with: banner = $banner, view = $view, model = $model, position = $position"
                        )
                        if (view is TKBannerFrameLayout) {
                            view.setBannerLeftRightMargin(dp2px(10f))
                            view.setBannerTextBottomMargin(dp2px(15f))
                            ImageLoader.getInstance().roundCorner(
                                context = this@Banner_Simple_Activity,
                                url = model.url,
                                imageView = view.getBannerImageView(),
                                radius = dp2px(4f)
                            )
                            view.setBannerText(model.title)
                        }
                    }
                })
            }
        }

        Looper.myQueue().addIdleHandler {
            binding.banner1.getViewPager()?.apply {
                binding.indicatorView.setupWithViewPager(this)
            }
            false
        }

    }


}