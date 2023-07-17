package com.lytredrock.model.login.loginData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/15 16:27
 * version: 1.0
 */
data class QRPic<T>(
    val code: Int,
    val `data`: QRPicData
)

data class QRPicData(
    val qrimg: String,
    val qrurl: String
)