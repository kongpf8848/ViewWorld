package com.github.kongpf8848.viewworld.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.utis.LogUtils
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val TAG: String =javaClass.simpleName
    protected lateinit var binding: T

    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateStart(savedInstanceState)
        super.onCreate(savedInstanceState)
        LogUtils.d(TAG, "onCreate called")
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        onCreateEnd(savedInstanceState)
    }

    protected open fun onCreateStart(savedInstanceState: Bundle?) {}
    protected open fun onCreateEnd(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "onDestroy() called")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.d(TAG, "onRestart() called")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        LogUtils.d(TAG, "onNewIntent() called")
    }

    @JvmOverloads
    fun startActivity(cls: Class<*>?, finishThis: Boolean = false) {
        val intent = Intent(this, cls)
        startActivity(intent)
        if (finishThis) {
            finish()
        }
    }

    fun startActivity(target: Class<*>?, vararg sharedElements: Pair<View?, String?>?) {
        val intent = Intent(this, target)
        val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedElements)
        startActivity(intent, transitionActivityOptions.toBundle())
    }

    /**++++++++++++++++++++++++++++++++++++状态栏,导航栏相关++++++++++++++++++++++++++++++++++++++++++*/
    /*
     此方法在setContentView之后调用,如需定制，则设置enableWhiteStatusBar为false,然后覆盖customInitStatusBar方法
   */
    override fun onContentChanged() {
        super.onContentChanged()
        try {
            if (enableStatusBar()) {
                ImmersionBar.with(this)
                        .fitsSystemWindows(fitsSystemWindows())
                        .statusBarColor(statusBarColor())
                        .statusBarDarkFont(statusBarDarkFont())
                        .navigationBarColor(navigationBarColor(), navigationBarAlpha())
                        .navigationBarDarkIcon(navigationBarDarkIcon())
                        .keyboardEnable(keyboardEnable())
                        .init()
            } else {
                customInitStatusBar()
            }
        } catch (ex: Exception) {
            LogUtils.d("initStatusBar Exception:" + ex.message)
        }
    }

    /**
     * 针对一些页面，如聊天界面，防止沉浸式状态栏全屏和键盘有冲突，键盘弹出时布局无法上移，可以采用此方法
     * @param color
     * @param isDarkFont
     */
    protected fun setStatusBarColorSpecial(@ColorInt color: Int, isDarkFont: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            if (isDarkFont) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }

    protected fun setStatusBarColorInt(@ColorInt color: Int, isDarkFont: Boolean) {
        ImmersionBar.with(this).statusBarColorInt(color).statusBarDarkFont(isDarkFont).init()
    }

    protected fun initStatusBar(@IdRes titleBarId: Int, @ColorRes statusBarColor: Int = android.R.color.transparent) {
        ImmersionBar.with(this)
                .titleBarMarginTop(titleBarId)
                .statusBarColor(statusBarColor)
                .navigationBarColor(navigationBarColor(), navigationBarAlpha())
                .navigationBarDarkIcon(navigationBarDarkIcon())
                .keyboardEnable(keyboardEnable())
                .init()
    }

    protected open fun enableStatusBar(): Boolean {
        return true
    }

    protected open fun fitsSystemWindows(): Boolean {
        return true
    }

    protected open fun statusBarColor(): Int {
        return R.color.colorPrimaryDark
    }

    protected open fun navigationBarColor(): Int {
        return R.color.white
    }

    protected fun navigationBarAlpha(): Float {
        return 0.5f
    }

    protected open fun statusBarDarkFont(): Boolean {
        return false
    }

    protected open fun navigationBarDarkIcon(): Boolean {
        return true
    }

    protected fun keyboardEnable(): Boolean {
        return true
    }

    protected open fun customInitStatusBar() {}
}