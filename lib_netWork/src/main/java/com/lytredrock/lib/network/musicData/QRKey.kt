package com.lytredrock.lib.network.musicData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/15 16:22
 * version: 1.0
 */
data class QRKey<T>(
    val code: Int,
    val `data`: QRData
)

data class QRData(
    val code: Int,
    val unikey: String
)