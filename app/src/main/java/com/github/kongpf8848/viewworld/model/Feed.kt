package com.github.kongpf8848.viewworld.model

import java.io.Serializable

data class Feed (
    val date: String? = null,
    val stories: List<Story>,
    val top_stories: List<TopStory>
):Serializable