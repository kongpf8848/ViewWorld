package com.github.kongpf8848.viewworld.model

data class TopStory(
    var id:Long= 0,
    var type:Int = 0,
    var title: String="",
    var image: String = "",
    var ga_prefix: String = "",
    var image_hue: String = "",
    var hint: String = "",
    var url: String = ""
)