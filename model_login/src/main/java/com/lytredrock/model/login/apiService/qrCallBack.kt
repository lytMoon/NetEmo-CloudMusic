package com.lytredrock.model.login.apiService

import com.lytredrock.model.login.loginData.CodeNum
import com.lytredrock.model.login.loginData.QRLast

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