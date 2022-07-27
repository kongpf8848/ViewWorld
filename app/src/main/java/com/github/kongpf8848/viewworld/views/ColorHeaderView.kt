package com.github.kongpf8848.viewworld.views

import android.content.Context
import android.widget.FrameLayout
import android.view.LayoutInflater
import com.github.kongpf8848.viewworld.R
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView

class ColorHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    lateinit var colorHeaderText: TextView
    lateinit var colorHeaderImage: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_color_header, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        colorHeaderText = findViewById(R.id.colorHeaderText)
        colorHeaderImage = findViewById(R.id.colorHeaderImage)
    }

    fun setImage(@DrawableRes resId: Int) {
        colorHeaderText.text = ""
        Glide.with(context).load(resId).circleCrop().into(colorHeaderImage)
        updateUI()
    }

    fun setImage(url: String) {
        colorHeaderText.text = ""
        Glide.with(context).load(url).circleCrop().into(colorHeaderImage)
        updateUI()
    }

    fun setData(text: CharSequence?, color: Int) {
        val drawable = GradientDrawable().apply {
            setColor(color)
            shape = GradientDrawable.OVAL
        }
        colorHeaderText.background = drawable
        colorHeaderText.text = text
        Glide.with(context).clear(colorHeaderImage)
        updateUI()
    }

    private fun updateUI() {
        val text = colorHeaderText.text.toString()
        if (TextUtils.isEmpty(text)) {
            colorHeaderImage.visibility = VISIBLE
            colorHeaderText.visibility = INVISIBLE
        } else {
            colorHeaderImage.visibility = INVISIBLE
            colorHeaderText.visibility = VISIBLE
        }
    }

}