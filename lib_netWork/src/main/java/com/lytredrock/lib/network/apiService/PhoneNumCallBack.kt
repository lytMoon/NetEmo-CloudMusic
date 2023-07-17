package com.lytredrock.lib.network.apiService

import com.lytredrock.lib.network.musicData.CodeNum

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 10:24
 * version: 1.0
 */
interface PhoneNumCallBack {
    fun onRespond(data: CodeNum)
    fun onFailed(e:String)
}