package com.github.kongpf8848.viewworld.model

import java.io.Serializable

data class Story(
    val id:Long= 0,
    val type:Int = 0,
    val title: String="",
    val images: List<String>?=null,
    val ga_prefix: String = "",
    val image_hue: String = "",
    val hint: String = "",
    val url: String = ""
):Serializable