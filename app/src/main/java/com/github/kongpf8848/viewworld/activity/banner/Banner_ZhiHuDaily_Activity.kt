package com.github.kongpf8848.viewworld.activity.banner

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import com.github.kongpf8848.tkbanner.TKBanner
import com.github.kongpf8848.viewworld.extension.load
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.model.TopStory
import com.github.kongpf8848.viewworld.utis.LogUtils
import kotlinx.android.synthetic.main.activity_banner_zhihu_daily.*
import kotlinx.android.synthetic.main.activity_banner_zhihu_daily.toolbar

class Banner_ZhiHuDaily_Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_banner_zhihu_daily
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
            setData(layoutId = R.layout.item_banner_zhihu_daily,models = getBannerData())
            /**
             * 设置ViewPager Page切换事件
             */
            setOnPageChangeListener(indicator)
            /**
             * 设置ViewPager Page点击事件
             */
            setOnBannerItemClickListener(object : TKBanner.OnBannerItemClickListener<TopStory> {
                override fun onBannerItemClick(banner: TKBanner, model: TopStory, position: Int) {
                    LogUtils.d(
                        TAG,
                        "onBannerItemClick() called with: banner = $banner, model = $model, position = $position"
                    )
                }
            })
            /**
             * 填充ViewPager Page
             */
            setAdapter(object : TKBanner.Adapter<TopStory> {
                override fun fillBannerItem(
                    banner: TKBanner,
                    view: View,
                    model: TopStory,
                    position: Int
                ) {
                    LogUtils.d(
                        TAG,
                        "fillBannerItem() called with: banner = $banner, view = $view, model = $model, position = $position"
                    )
                    val imageView:ImageView=view.findViewById(R.id.hero_view)
                    val mask:View=view.findViewById(R.id.mask)
                    val titleView:TextView=view.findViewById(R.id.title_view)
                    val hintView:TextView=view.findViewById(R.id.hint_view)
                    imageView.load(
                        context=this@Banner_ZhiHuDaily_Activity,
                        url=model.image,
                        successCallback = {
                          if(it is BitmapDrawable){
                              Palette.from(it.bitmap).generate {
                                  it?.apply {
                                      val color=getMutedColor(Color.TRANSPARENT)
                                      mask.setBackgroundColor(color)
                                  }
                              }
                          }
                        },
                        failCallback = {

                        }

                    )
                    titleView.text=model.title
                    hintView.text=model.hint

                }
            })
        }
        Looper.myQueue().addIdleHandler {
            indicator.setUp(count = banner.getRealCount())
            false
        }


    }


    private fun getBannerData(): List<TopStory> {
        return mutableListOf<TopStory>().apply {
            add(
                TopStory(
                    id = 9735445,
                    type = 0,
                    title = "中国有哪些「值得特地走一趟」的高速公路？",
                    image = "https://pic2.zhimg.com/v2-0f1f357911897cbd8c6bb970cb9162e1.jpg?source=8673f162",
                    ga_prefix = "042707",
                    image_hue = "0xb39879",
                    hint = "作者 / 在远方的阿伦",
                    url = "https://daily.zhihu.com/story/9735445"
                )
            )
            add(
                TopStory(
                    id = 9735409,
                    type = 0,
                    title = "如何纯手工自己打造一款机械表?",
                    image = "https://pic4.zhimg.com/v2-f5106153eb885291b93586245b4f432a.jpg?source=8673f162",
                    ga_prefix = "042607",
                    image_hue = "0xb3a17d",
                    hint = "作者 / 李存at设计",
                    url = "https://daily.zhihu.com/story/9735409"
                )
            )
            add(
                TopStory(
                    id = 9735337,
                    type = 0,
                    title = "有哪些好玩的 switch 游戏？",
                    image = "https://pic4.zhimg.com/v2-b87c6b2d6495bb96e121025628d8a4d8.jpg?source=8673f162",
                    ga_prefix = "042407",
                    image_hue = "0x543c24",
                    hint = "作者 / 风语叔",
                    url = "https://daily.zhihu.com/story/9735337"
                )
            )

            add(
                TopStory(
                    id = 9735387,
                    type = 0,
                    title = "HR 问你目前拿到哪几个 offer 了怎么回答好？",
                    image = "https://pic1.zhimg.com/v2-3bf1e5fae9ae9e16be0c5f1e81f6d7bb.jpg?source=8673f162",
                    ga_prefix = "042507",
                    image_hue = "0x393b29",
                    hint = "作者 / 弗兰克扬",
                    url = "https://daily.zhihu.com/story/9735387"
                )
            )

            add(
                TopStory(
                    id = 9735304,
                    type = 0,
                    title = "大家都内推，内推还有什么意义？",
                    image = "https://pic3.zhimg.com/v2-9145abcce9999ce65560f6eee35c932a.jpg?source=8673f162",
                    ga_prefix = "042307",
                    image_hue = "0x2e2320",
                    hint = "作者 / 章牧之",
                    url = "https://daily.zhihu.com/story/9735304"
                )
            )
        }
    }



}