package com.lytredrock.model.login.apiservice

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 16:00
 * version: 1.0
 */
interface IVerifyCodeInfo {
    fun onRespond(flag: Boolean)
    fun onFailed(e: String?)
}