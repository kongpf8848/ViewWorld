package com.github.kongpf8848.viewworld.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.kongpf8848.viewworld.utis.ImageLoader

fun ImageView.load(
    context: Context,
    url: String,
    requestOptions: RequestOptions? = null,
    successCallback: ((resource: Drawable) -> Unit)? = null,
    failCallback: ((e: GlideException?) -> Unit)? = null
) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any,
            target: Target<Drawable>,
            isFirstResource: Boolean
        ): Boolean {
            failCallback?.invoke(e)
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            successCallback?.invoke(resource)
            return false
        }
    }
    ImageLoader.getInstance().load(
        context = context,
        url = url,
        imageView = this,
        requestOptions = requestOptions,
        listener = listener
    )

}