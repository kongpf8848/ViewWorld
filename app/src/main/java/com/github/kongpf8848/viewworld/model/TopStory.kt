package com.github.kongpf8848.viewworld.model

import java.io.Serializable

data class TopStory(
    val id:Long= 0,
    val type:Int = 0,
    val title: String="",
    val image: String = "",
    val ga_prefix: String = "",
    val image_hue: String = "",
    val hint: String = "",
    val url: String = ""
):Serializable