package com.github.kongpf8848.viewworld.activity.banner

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.viewpager.widget.ViewPager
import com.github.kongpf8848.tkbanner.TKBanner
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.model.Geo
import com.github.kongpf8848.viewworld.model.LiVideoBanner
import com.github.kongpf8848.viewworld.model.UserInfo
import com.github.kongpf8848.viewworld.utis.ImageLoader
import com.github.kongpf8848.viewworld.utis.LogUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_banner_huxiu.banner
import kotlinx.android.synthetic.main.activity_banner_huxiu.toolbar
import kotlinx.android.synthetic.main.activity_banner_livideo.*


class Banner_LiVideo_Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_banner_livideo
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        toolbar.setNavigationOnClickListener { finish() }

        val models=getBannerData()

        banner.apply {
            setAutoPlayAble(false)
            /**
             * 设置数据
             */
            setData(layoutId = R.layout.frag_main_top_page_item_headline, models = models)
            setOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                    indicator.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    indicator.onPageSelected(position)
                    val view=banner.getView(position)
                    if(view!=null){
                        val img_headline_item:ImageView=view.findViewById(R.id.img_headline_item)

                        val video_player:VideoView=view.findViewById(R.id.video_player)
                        if(video_player.isPlaying){
                            video_player.stopPlayback()
                        }

                        video_player.setVideoURI(Uri.parse(models[position].coverVideo))
                        video_player.setOnPreparedListener { mediaPlayer: MediaPlayer ->
                            img_headline_item.visibility=View.GONE
                            mediaPlayer.isLooping = position >= (models.size-1)
                        }
                        video_player.setOnCompletionListener {

                        }
                        video_player.start()
                    }
                }

            })

            /**
             * 设置ViewPager Page点击事件
             */
            setOnBannerItemClickListener(object :
                TKBanner.OnBannerItemClickListener<LiVideoBanner> {
                override fun onBannerItemClick(
                    banner: TKBanner,
                    model: LiVideoBanner,
                    position: Int
                ) {
                    LogUtils.d(
                        TAG,
                        "onBannerItemClick() called with: banner = $banner, model = $model, position = $position"
                    )
                }
            })
            /**
             * 填充ViewPager Page
             */
            setAdapter(object : TKBanner.Adapter<LiVideoBanner> {
                override fun fillBannerItem(
                    banner: TKBanner,
                    view: View,
                    model: LiVideoBanner,
                    position: Int
                ) {
                    LogUtils.d(
                        TAG,
                        "fillBannerItem() called with: banner = $banner, view = $view, model = $model, position = $position"
                    )
                    val img_headline_item: ImageView = view.findViewById(R.id.img_headline_item)
                    val ll_item_video_list_loc:LinearLayout=view.findViewById(R.id.ll_item_video_list_loc)
                    val tv_item_video_list_loc:TextView=view.findViewById(R.id.tv_item_video_list_loc)
                    val tv_headline_head:TextView=view.findViewById(R.id.tv_headline_head)
                    img_headline_item.visibility=View.VISIBLE
                    ImageLoader.getInstance().load(
                        context = baseActivity,
                        url = model.pic,
                        imageView = img_headline_item
                    )
                    if(!TextUtils.isEmpty(model.geo.showName)){
                        ll_item_video_list_loc.visibility=View.VISIBLE
                        tv_item_video_list_loc.text=model.geo.showName
                    }
                    else{
                        ll_item_video_list_loc.visibility=View.INVISIBLE
                    }
                    tv_headline_head.text=model.name


                }
            })
        }
        Looper.myQueue().addIdleHandler {
            indicator.setUp(banner.getRealCount())
            false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    private fun getBannerData() = listOf(
        LiVideoBanner(
            contId = "1731465",
            name = "太淡定了！高考第一天清晨，衡水中学正常跑操上自习",
            pic = "https://image1.pearvideo.com/cont/20210607/cont-1731465-12591466.jpg",
            userInfo = UserInfo(
                userId = "14190387",
                nickname = "上学记",
                pic = "https://image.pearvideo.com/node/3604-12389005-logo.png",
                level = "2"
            ),
            geo = Geo(
                namePath = "中国,河北省,衡水市,桃城区",
                showName = "拍客亲切喊我晓白哥·发自河北省衡水市",
                address = "",
                loc = "0.0,0.0|中国,河北省,衡水市,桃城区"
            ),
            coverVideo = "https://video.pearvideo.com/head/20210607/cont-1731465-15690020.mp4"
        ),
        LiVideoBanner(
            contId = "1731471",
            name = "54岁“考王”第25次高考：考上川大为止，身体不行才投降",
            pic = "https://image2.pearvideo.com/cont/20210607/cont-1731471-12591470.jpg",
            userInfo = UserInfo(
                userId = "14190387",
                nickname = "上学记",
                pic = "https://image.pearvideo.com/node/3604-12389005-logo.png",
                level = "2"
            ),
            geo = Geo(
                namePath = "中国,四川省,成都市,金牛区",
                showName = "拍客天府四川·发自四川省成都市",
                address = "",
                loc = "0.0,0.0|中国,四川省,成都市,金牛区"
            ),
            coverVideo = "https://video.pearvideo.com/head/20210607/cont-1731471-15690038.mp4"
        ),
        LiVideoBanner(
            contId = "1731454",
            name = "3万乡亲自发组26方阵为考生壮行：敲锣打鼓比过年更隆重",
            pic = "https://image.pearvideo.com/cont/20210607/cont-1731454-12591402.jpg",
            userInfo = UserInfo(
                userId = "14190387",
                nickname = "上学记",
                pic = "https://image.pearvideo.com/node/3604-12389005-logo.png",
                level = "2"
            ),
            geo = Geo(namePath = "中国", showName = "", address = "", loc = "0.0,0.0|中国"),
            coverVideo = "https://video.pearvideo.com/head/20210607/cont-1731454-15689820.mp4"
        ),
        LiVideoBanner(
            contId = "1731333",
            name = "高考加油，越过山丘！",
            pic = "https://image.pearvideo.com/cont/20210607/cont-1731333-12591411.jpg",
            userInfo = UserInfo(
                userId = "14190387",
                nickname = "上学记",
                pic = "https://image.pearvideo.com/node/3604-12389005-logo.png",
                level = "2"
            ),
            geo = Geo(namePath = "中国", showName = "", address = "", loc = "0.0,0.0|中国"),
            coverVideo = "https://video.pearvideo.com/head/20210607/cont-1731333-15689849.mp4"
        ),
        LiVideoBanner(
            contId = "1717716",
            name = "揭秘天价宠物锦鲤产业链：中国土豪270万抢一条鱼",
            pic = "https://image.pearvideo.com/cont/20210126/cont-1717716-12546342.jpg",
            userInfo = UserInfo(
                userId = "15858474",
                nickname = "城市地理",
                pic = "https://image.pearvideo.com/node/3643-12543920-logo.png",
                level = "2"
            ),
            geo = Geo(namePath = "中国", showName = "", address = "", loc = "0.0,0.0|中国"),
            coverVideo = "https://video.pearvideo.com/head/20210126/cont-1717716-15588304.mp4"
        )
    )
}