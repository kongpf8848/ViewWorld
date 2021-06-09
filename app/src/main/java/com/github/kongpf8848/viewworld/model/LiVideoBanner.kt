package com.github.kongpf8848.viewworld.model

import java.io.Serializable

data class LiVideoBanner(
    val contId: String,
    val name: String,
    val pic: String,
    val userInfo: UserInfo,
    val geo: Geo,
    val coverVideo: String
) : Serializable

data class UserInfo(
    val userId: String,
    val nickname: String,
    val pic: String,
    val level: String
) : Serializable

data class Geo(
    val namePath: String,
    val showName: String,
    val address: String,
    val loc: String
) : Serializable
