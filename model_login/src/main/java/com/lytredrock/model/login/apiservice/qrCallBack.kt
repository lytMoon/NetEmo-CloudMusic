package com.lytredrock.model.login.apiservice

import com.lytredrock.model.login.logindata.QRLast

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 16:22
 * version: 1.0
 */
interface qrCallBack {

    fun onRespond(data: QRLast)
    fun onFailed(e:String)
}