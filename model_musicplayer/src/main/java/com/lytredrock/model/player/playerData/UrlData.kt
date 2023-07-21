package com.lytredrock.model.player.playerData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 16:06
 * version: 1.0
 */
data class UrlData<T>(
    val code: Int,
    val `data`: List<Data>
)

data class Data(
    val id: Int,
    val type: String,
    val url: String,
)

