package com.lytredrock.model.login.apiservice

/**
 * description ：接口回调，用于拿到网络请求的数据后的回调
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/16 14:37
 * version: 1.0
 */
interface MusicInfoCallBack {
    fun onRespond(qrimg: String)
    fun onFailed(e:String)
}
