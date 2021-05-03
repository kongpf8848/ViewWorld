package com.github.kongpf8848.viewworld.activity.banner

import android.os.Bundle
import android.os.Looper
import android.view.View
import com.github.kongpf8848.tkbanner.TKBanner
import com.github.kongpf8848.tkbanner.TKBannerFrameLayout
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.extension.dp2px
import com.github.kongpf8848.viewworld.model.BannerItem
import com.github.kongpf8848.viewworld.utis.ImageLoader
import com.github.kongpf8848.viewworld.utis.LogUtils
import kotlinx.android.synthetic.main.activity_banner_pingwest.*


class Banner_Pingwest_Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_banner_pingwest
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener { finish() }

        banner.apply {
            /**
             * 设置轮播间隔
             */
            setAutoPlayInterval(3000)
            /**
             * 设置数据
             */
            setData(models = getBannerData())
            /**
             * 设置ViewPager Page切换事件
             */
            setOnPageChangeListener(indicator)
            /**
             * 设置ViewPager Page点击事件
             */
            setOnBannerItemClickListener(object : TKBanner.OnBannerItemClickListener<BannerItem> {
                override fun onBannerItemClick(banner: TKBanner, model: BannerItem, position: Int) {
                    LogUtils.d(
                        TAG,
                        "onBannerItemClick() called with: banner = $banner, model = $model, position = $position"
                    )
                }
            })
            /**
             * 填充ViewPager Page
             */
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
                        view.setBannerTextBottomMargin(dp2px(25f))
                        ImageLoader.getInstance().load(
                            context = baseActivity,
                            url = model.url,
                            imageView = view.getBannerImageView()
                        )
                        view.setBannerText(model.title)
                    }
                }
            })
        }
        Looper.myQueue().addIdleHandler {
            indicator.setUp(count = banner.getRealCount())
            false
        }


    }

    private fun getBannerData() = listOf(
        BannerItem(
            id = 1,
            url = "https://cdn.pingwest.com/portal/2021/04/30/N6P1z95711DSm7pz1P6hbsJSB6AmGN72.JPG?x-oss-process=style/article-thumb-md",
            title = "Redmi K40游戏增强版：不是传统意义上的游戏手机"
        ),
        BannerItem(
            id = 2,
            url = "https://cdn.pingwest.com/portal/2021/04/30/75iczp7yPAn23ByYe3ZA87PhFsB7E6_A.jpeg?x-oss-process=style/article-thumb-md",
            title = "马斯克的妈妈入驻小红书了，你猜是不是来给儿子PR的？"
        ),
        BannerItem(
            id = 3,
            url = "https://cdn.pingwest.com/portal/2021/05/01/a30cf634b0c0f74ee584a211d0d00bd5.png?x-oss-process=style/article-thumb-md",
            title = "TikTok空降华人CEO：年仅30多岁，曾就职脸书，被雷军戏称“第二帅男神”"
        ),
        BannerItem(
            id = 4,
            url = "https://cdn.pingwest.com/portal/2021/04/29/5pn403R2N40mGKbwr8kC154f5Bkit699.jpeg?x-oss-process=style/article-thumb-md",
            title = "很可能是3年后人类唯一在运行的空间站，由中国开始搭建"
        ),
        BannerItem(
            id = 5,
            url = "https://cdn.pingwest.com/portal/2021/04/28/by6bjQrmkRSAAw6766z56ZCHeaTN7Djw.jpeg?x-oss-process=style/article-thumb-md",
            title = "一支中国疫苗怎样在海外着陆"
        ),
        BannerItem(
            id = 6,
            url = "https://cdn.pingwest.com/portal/2021/04/27/Nhr6ZCb3G_5XmFx5Exwh2R7r31416M2B.jpeg?x-oss-process=style/article-thumb-md",
            title = "聚划算，剑指“价值电商”"
        )
    )
}