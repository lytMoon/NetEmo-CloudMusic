package com.lytredrock.model.login.apiservice

import com.lytredrock.model.login.logindata.CodeNum

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