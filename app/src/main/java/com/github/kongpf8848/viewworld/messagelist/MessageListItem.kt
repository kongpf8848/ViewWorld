package com.github.kongpf8848.viewworld.messagelist

data class MessageListItem(
    val uniqueId:Long,
    val subject:String,
    var isRead: Boolean,
    var isStarred: Boolean,
)
