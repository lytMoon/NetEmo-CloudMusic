package com.lytredrock.model.player.apiservice

import com.lytredrock.model.player.playerData.Data
import com.lytredrock.model.player.playerData.UrlData


/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 16:23
 * version: 1.0
 */
interface UrlCallBack {
    fun onRespond(t: UrlData<Data>)
    fun onFailed(e:String)
}