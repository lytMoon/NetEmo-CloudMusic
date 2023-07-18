package com.lytredrock.model.research.apiService

import com.lytredrock.model.research.musicdata.Result
import com.lytredrock.model.research.musicdata.SearchSongData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/18 14:31
 * version: 1.0
 */
interface SongCallBack {
    fun onRespond(t: SearchSongData<Result>)
    fun onFailed(e: String)
}