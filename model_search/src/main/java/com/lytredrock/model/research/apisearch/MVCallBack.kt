package com.lytredrock.model.research.apisearch

import com.lytredrock.model.research.musicdata.MVData
import com.lytredrock.model.research.musicdata.MVResult

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 15:46
 * version: 1.0
 */
interface MVCallBack {
    fun onRespond(t: MVData<MVResult>)
    fun onFailed(e: String)
}