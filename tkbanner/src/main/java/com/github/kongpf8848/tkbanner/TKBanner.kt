package com.github.kongpf8848.tkbanner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils.TruncateAt
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager.widget.ViewPager.PageTransformer
import java.lang.ref.WeakReference

class TKBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), OnPageChangeListener {

    private var mViewPager: TKBannerViewPager? = null
    private var mShowIndicator=true
    private var mPointContainerBackgroundDrawable:Drawable? = null
    private var mPointContainer: LinearLayout? = null
    private var mPointContainerLeftRightPadding:Int = 0
    private var mPointContainerTopBottomPadding:Int = 0
    private var mPointLeftRightMargin = 0
    private var mPointTopBottomMargin = 0
    private var mPointDrawableResId = 0
    private var mIsNumberIndicator = false
    private var mNumberIndicatorTv:TextView?=null
    private var mNumberIndicatorTextColor = 0
    private var mNumberIndicatorTextSize = 0
    private var mNumberIndicatorBackground: Drawable? = null


    private var mAutoPlayAble = false
    private var mAutoPlayInterval = 0L
    private var mPageMargin=0
    private var mClipChildren=true
    private var mClipToPadding=true
    private var mPageChangeDuration:Int = 0
    private var mIndicatorGravity=0
    private var mOverScrollMode = 0
    private var mReverseDrawingOrder=false
    private var mPageTransformer:PageTransformer?=null

    private var mAutoPlayTask: AutoPlayTask? = null
    private var mOnBannerItemClickListener: OnBannerItemClickListener<*>?=null
    private var mAdapter: Adapter<*>? = null
    private var mModels: List<*>? = null
    private var mViews: List<View>?=null
    private var mLayoutResId=R.layout.tk_banner_item
    private var mOnPageChangeListener: OnPageChangeListener? = null

    private var currentPosition=0

    init {
        this.mAutoPlayAble = true
        this.mAutoPlayInterval = 3000L
        this.mShowIndicator=true
        this.mPageChangeDuration =800
        this.mOverScrollMode= View.OVER_SCROLL_NEVER
        this.mIndicatorGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL

        this.mPointDrawableResId=R.drawable.tk_banner_dot
        this.mPointLeftRightMargin = TKBannerUtils.dp2px(context, 3.0f)
        this.mPointTopBottomMargin = TKBannerUtils.dp2px(context, 6.0f)

        this.mNumberIndicatorTextColor= Color.WHITE
        this.mNumberIndicatorTextSize= TKBannerUtils.dp2px(context,14f)

        this.mAutoPlayTask = AutoPlayTask(this)

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TKBanner)
        for (i in 0 until typedArray.indexCount) {
            val attr=typedArray.getIndex(i)
            if (attr == R.styleable.TKBanner_banner_autoPlayAble) {
                mAutoPlayAble = typedArray.getBoolean(attr, mAutoPlayAble)
            } else if (attr == R.styleable.TKBanner_banner_autoPlayInterval) {
                mAutoPlayInterval = typedArray.getInt(attr, mAutoPlayInterval.toInt()).toLong()
            }else if(attr==R.styleable.TKBanner_banner_showIndicator){
                mShowIndicator=typedArray.getBoolean(attr,mShowIndicator)
            }
            else if (attr == R.styleable.TKBanner_banner_indicatorGravity) {
                mIndicatorGravity = typedArray.getInt(attr, mIndicatorGravity)
            }
            else if(attr==R.styleable.TKBanner_banner_pointContainerLeftRightPadding){
                mPointContainerLeftRightPadding=typedArray.getDimensionPixelSize(attr,0)
            }else if(attr==R.styleable.TKBanner_banner_pointContainerTopBottomPadding){
                mPointContainerTopBottomPadding=typedArray.getDimensionPixelSize(attr,0)
            }
            else if (attr == R.styleable.TKBanner_banner_pointDrawable) {
                mPointDrawableResId = typedArray.getResourceId(attr,mPointDrawableResId)
            } else if (attr == R.styleable.TKBanner_banner_pointLeftRightMargin) {
                mPointLeftRightMargin = typedArray.getDimensionPixelSize(attr, mPointLeftRightMargin)
            } else if (attr == R.styleable.TKBanner_banner_pointTopBottomMargin) {
                mPointTopBottomMargin = typedArray.getDimensionPixelSize(attr, mPointTopBottomMargin)
            }else if(attr==R.styleable.TKBanner_banner_isNumberIndicator){
                mIsNumberIndicator=typedArray.getBoolean(attr,false)
            }
            else if(attr==R.styleable.TKBanner_banner_numberIndicatorTextColor){
                mNumberIndicatorTextColor=typedArray.getColor(attr,this.mNumberIndicatorTextColor)
            }
            else if(attr==R.styleable.TKBanner_banner_numberIndicatorTextSize){
                mNumberIndicatorTextSize=typedArray.getDimensionPixelSize(attr,this.mNumberIndicatorTextSize)
            }
            else if(attr==R.styleable.TKBanner_banner_numberIndicatorBackground){
                mNumberIndicatorBackground=typedArray.getDrawable(attr)
            }
        }
        typedArray.recycle()

        initView()
    }

    private fun initView(){
        if(!mShowIndicator){
            return
        }

        val pointContainerRl = RelativeLayout(context)
        if(mPointContainerBackgroundDrawable!=null){
            pointContainerRl.background=mPointContainerBackgroundDrawable
        }
        pointContainerRl.setPadding(mPointContainerLeftRightPadding,mPointContainerTopBottomPadding,mPointContainerLeftRightPadding,mPointContainerTopBottomPadding)
        val pointContainerLayoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (this.mIndicatorGravity and Gravity.VERTICAL_GRAVITY_MASK == Gravity.TOP) {
            pointContainerLayoutParams.addRule(ALIGN_PARENT_TOP)
        } else {
            pointContainerLayoutParams.addRule(ALIGN_PARENT_BOTTOM)
        }
        this.addView(pointContainerRl, pointContainerLayoutParams)

        val indicatorLayoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        indicatorLayoutParams.addRule(CENTER_VERTICAL)

        val horizontalGravity: Int = mIndicatorGravity and Gravity.HORIZONTAL_GRAVITY_MASK
        if (horizontalGravity == Gravity.LEFT) {
            indicatorLayoutParams.addRule(ALIGN_PARENT_LEFT)
        } else if (horizontalGravity == Gravity.RIGHT) {
            indicatorLayoutParams.addRule(ALIGN_PARENT_RIGHT)
        } else {
            indicatorLayoutParams.addRule(CENTER_HORIZONTAL)
        }

        if(mIsNumberIndicator){
            this.mNumberIndicatorTv = TextView(context).apply {
                id = R.id.banner_indicatorId
                gravity = Gravity.CENTER
                isSingleLine = true
                ellipsize = TruncateAt.END
                setTextColor(mNumberIndicatorTextColor)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, mNumberIndicatorTextSize.toFloat())
                visibility = View.INVISIBLE
                if (mNumberIndicatorBackground != null) {
                    background =mNumberIndicatorBackground
                }
            }
            pointContainerRl.addView(this.mNumberIndicatorTv, indicatorLayoutParams)
        }
        else{
            this.mPointContainer= LinearLayout(context).apply {
                id=R.id.banner_indicatorId
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
            }
            pointContainerRl.addView(this.mPointContainer, indicatorLayoutParams)
        }
    }


    /**
     * 初始化指示符
     */
    private fun initIndicator(){
        if(!mShowIndicator){
            return
        }
       this.mPointContainer?.apply {
           removeAllViews()
           if (mViews!!.size > 1) {
               val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
               lp.setMargins(mPointLeftRightMargin, mPointTopBottomMargin, mPointLeftRightMargin, mPointTopBottomMargin)
               for (i in 0 until getRealCount()) {
                   val imageView = ImageView(this.context)
                   imageView.layoutParams = lp
                   imageView.setImageResource(mPointDrawableResId)
                   addView(imageView)
               }
           }
       }
    }
    /**
     * 初始化ViewPager
     */
    private fun initViewPager(){
        if (mViewPager != null && this == mViewPager!!.parent) {
            removeView(mViewPager)
            mViewPager = null
        }

        mViewPager = TKBannerViewPager(this.context).apply {
            clipChildren=mClipChildren
            clipToPadding=mClipToPadding
            overScrollMode=mOverScrollMode
            pageMargin=mPageMargin
            setPageChangeDuration(mPageChangeDuration)
            if(mPageTransformer!=null){
                setPageTransformer(mReverseDrawingOrder,mPageTransformer)
            }
            adapter = TKPagerAdapter()
            addOnPageChangeListener(this@TKBanner)
        }
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        this@TKBanner.addView(mViewPager, 0, layoutParams)
        if (mAutoPlayAble) {
            mViewPager!!.currentItem = 1
            startAutoPlay()
        }
        else
        {
            switchToPoint(0)
        }
    }


    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) {
            startAutoPlay()
        } else if (visibility == View.GONE || visibility == View.INVISIBLE) {
            stopAutoPlay()
        }
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAutoPlay()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAutoPlay()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (mAutoPlayAble) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    stopAutoPlay()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    startAutoPlay()
                }
                else -> {
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setData(resIds: IntArray){
        val views = mutableListOf<View>()
        val models= mutableListOf<Int>()
        for (i in resIds.indices) {
            views.add(TKBannerUtils.getItemImageView(context,resIds[i]))
            models.add(i)
        }
        if(resIds.size<=1){
            mAutoPlayAble=false
        }

        if(mAutoPlayAble){
            views.add(0,TKBannerUtils.getItemImageView(context,resIds[resIds.size-1]))
            views.add(TKBannerUtils.getItemImageView(context,resIds[0]))
        }
        this.setData(
            views=views,
            models = models
        )
    }

    fun setData(layoutId:Int?=null,models: List<Any?>){
        val views = mutableListOf<View>()
        val layoutResId=layoutId?:R.layout.tk_banner_item
        for (i in models.indices) {
            views.add(View.inflate(this.context, layoutResId, null))
        }
        if(models.size<=1){
            mAutoPlayAble=false
        }

        if(mAutoPlayAble){
            views.add(0,View.inflate(this.context, layoutResId, null))
            views.add(View.inflate(this.context,layoutResId, null))
        }
        this.setData(
            views=views,
            models = models
        )
    }

    fun setData(views: List<View>, models: List<Any?>) {
        mViews = views
        mModels = models
        initIndicator()
        initViewPager()
    }

    fun <M> setOnBannerItemClickListener(onBannerItemClickListener:OnBannerItemClickListener<M>) {
        this.mOnBannerItemClickListener =onBannerItemClickListener
    }

    fun <M> setAdapter(adapter: Adapter<M>) {
        mAdapter = adapter
    }

    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener
    }

    fun setAutoPlayAble(autoPlayAble: Boolean) {
        mAutoPlayAble = autoPlayAble
        stopAutoPlay()
        mViewPager?.adapter?.notifyDataSetChanged()
    }

    fun setAutoPlayInterval(autoPlayInterval: Int) {
        this.mAutoPlayInterval = autoPlayInterval.toLong()
    }

    fun setPagerMargin(pageMargin:Int){
        this.mPageMargin=pageMargin
    }

    fun setPageTransformer(reverseDrawingOrder: Boolean, transformer: PageTransformer?) {
       this.mReverseDrawingOrder=reverseDrawingOrder
        this.mPageTransformer=transformer
    }

    fun setViewPagerClipChildren(clipChildren:Boolean){
        this.mClipChildren=clipChildren

    }
    fun setViewPagerClipToPadding(clipToPadding:Boolean){
        this.mClipToPadding=clipToPadding
    }

    fun setPageChangeDuration(duration: Int) {
        mPageChangeDuration = duration
    }


    /**
     * 内部PagerAdapter
     */
    inner class TKPagerAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return  mViews!!.size
        }
        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val finalPosition=getRealPosition(position)
            val view = mViews!![position]
            view.setOnClickListener(object: DebouncingOnClickListener() {
                override fun doClick(v: View) {
                    this@TKBanner.mModels!![finalPosition]?.let {
                        (this@TKBanner.mOnBannerItemClickListener as OnBannerItemClickListener<Any>?)?.onBannerItemClick(
                            banner = this@TKBanner,
                            model = it,
                            position = finalPosition
                        )
                    }
                }
            })

            this@TKBanner.mModels!![finalPosition]?.let {
                (this@TKBanner.mAdapter as Adapter<Any>?)?.fillBannerItem(
                    banner = this@TKBanner,
                    view = view,
                    model = it,
                    position = finalPosition
                )
            }

            val viewParent = view.parent
            if (viewParent != null) {
                (viewParent as ViewGroup).removeView(view)
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

    }

    fun switchToNextPage() {
        mViewPager?.apply {
            currentItem=(currentItem+1)%adapter!!.count
        }
    }

    private fun switchToPoint(position: Int) {
        if(!mShowIndicator){
            return
        }

        if(!mIsNumberIndicator) {
            mPointContainer?.apply {
                if (mViews != null && mViews!!.size > 1) {
                    visibility = View.VISIBLE
                    for (i in 0 until childCount) {
                        getChildAt(i).isEnabled = (i == position)
                        getChildAt(i).requestLayout()
                    }
                } else {
                    visibility = View.GONE
                }
            }
        }else {
            mNumberIndicatorTv?.apply {
                if (mViews != null && mViews!!.size > 1) {
                    visibility = View.VISIBLE
                    text = "${position + 1}/${getRealCount()}"
                } else {
                    visibility = View.GONE
                }
            }
        }
    }

    private fun startAutoPlay() {
        this.stopAutoPlay()
        if (mAutoPlayAble) {
            postDelayed(this.mAutoPlayTask, this.mAutoPlayInterval)
        }
    }

    private fun stopAutoPlay() {
        mAutoPlayTask?.apply {
            removeCallbacks(this)
        }
    }

    /**
     * 用于填充ViewPager每个Page
     */
    interface Adapter<M>{
        fun fillBannerItem(
            banner: TKBanner,
            view:View,
            model: M,
            position: Int
        )
    }

    /**
     * ViewPager每个Page点击事件
     */
    interface OnBannerItemClickListener<M>{
        fun onBannerItemClick(
            banner: TKBanner,
            model: M,
            position: Int
        )
    }

    /**
     * 轮播Runnable
     */
    class AutoPlayTask(banner: TKBanner) : Runnable {
        private var mBanner: WeakReference<TKBanner>? = null

        init {
            mBanner = WeakReference<TKBanner>(banner)
        }

        override fun run() {
            val banner = mBanner?.get()
            if (banner != null) {
                banner.switchToNextPage()
                banner.startAutoPlay()
            }
        }
    }

    /**
     * 获取调整之后的位置
     */
    private fun getAdjustPosition(position:Int):Int{
        if(!mAutoPlayAble){
            return position
        }
        else {
            return when (position) {
                0 -> {
                    mViews!!.size-2
                }
                (mViews!!.size-1) -> {
                    1
                }
                else -> {
                    position-1
                }
            }
        }
    }

    /**
     * 获取真实的位置
     */
    private fun getRealPosition(position: Int):Int{
        if(!mAutoPlayAble){
            return position
        }
        else {
            return when (position) {
                0 -> {
                    mViews!!.size-3
                }
                (mViews!!.size-1) -> {
                    0
                }
                else -> {
                    position-1
                }
            }
        }
    }

    /**
     * 获取真实页面数量
     */
    fun getRealCount():Int{
        if(mAutoPlayAble){
            return mViews!!.size-2
        }
        else{
            return mViews!!.size
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if(mAutoPlayAble) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (currentPosition == 0 || currentPosition == (mViews!!.size -1)) {
                    mViewPager?.setCurrentItem(getAdjustPosition(position = currentPosition), false)
                }
            }
        }
        mOnPageChangeListener?.onPageScrollStateChanged(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val finalPosition=getRealPosition(position)
        mOnPageChangeListener?.onPageScrolled(finalPosition,positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        currentPosition = position
        val finalPosition=getRealPosition(position)
        switchToPoint(finalPosition)
        mOnPageChangeListener?.onPageSelected(finalPosition)
    }

    fun getViewPager(): TKBannerViewPager? {
        return mViewPager
    }


}