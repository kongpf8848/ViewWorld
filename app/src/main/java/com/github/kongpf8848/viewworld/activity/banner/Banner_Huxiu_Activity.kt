package com.github.kongpf8848.viewworld.activity.banner

import android.graphics.Outline
import android.graphics.Rect
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import com.github.kongpf8848.tkbanner.TKBanner
import com.github.kongpf8848.tkbanner.TKBannerFrameLayout
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.extension.dp2px
import com.github.kongpf8848.viewworld.model.BannerItem
import com.github.kongpf8848.viewworld.utis.ImageLoader
import com.github.kongpf8848.viewworld.utis.LogUtils
import kotlinx.android.synthetic.main.activity_banner_huxiu.*


class Banner_Huxiu_Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_banner_huxiu
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
                        view.setBannerLeftRightMargin(dp2px(16f))
                        view.setBannerTextBottomMargin(dp2px(8f))
                        view.getBannerRelativeLayout().apply {
                            outlineProvider = object : ViewOutlineProvider() {
                                override fun getOutline(view: View?, outline: Outline?) {
                                    outline?.setRoundRect(Rect(0, 0, view!!.width, view.height), dp2px(16f).toFloat())
                                }
                            }
                            clipToOutline = true
                        }
                        ImageLoader.getInstance().load(
                            context = baseActivity,
                            url = model.url,
                            imageView = view.getBannerImageView()
                        )
                        view.setBannerTextSize(20f)
                        view.setBannerText(model.title)
                    }
                }
            })
        }

    }

    private fun getBannerData() = listOf(
        BannerItem(
            id = 1,
            url = "https://img.huxiucdn.com/article/cover/202101/29/110925394581.jpg?imageView2/1/w/800/h/450/|imageMogr2/strip/interlace/1/quality/85/format/jpg",
            title = "芯片断供八个月后，华为现在怎么样了"
        ),
        BannerItem(
            id = 2,
            url = "https://img.huxiucdn.com/article/cover/202011/20/104111236465.jpg?imageView2/1/w/800/h/450/|imageMogr2/strip/interlace/1/quality/85/format/jpg",
            title = "互联网巨头进行大规模并购的可能性，急剧缩小了"
        ),
        BannerItem(
            id = 3,
            url = "https://img.huxiucdn.com/article/cover/202105/02/213242673499.jpg?imageView2/1/w/800/h/450/|imageMogr2/strip/interlace/1/quality/85/format/jpg",
            title = "最容易出十倍牛股的“曲棍球增长模型”"
        )
    )
}