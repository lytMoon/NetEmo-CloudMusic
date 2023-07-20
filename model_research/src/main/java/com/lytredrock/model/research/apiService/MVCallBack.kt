package com.lytredrock.model.research.apiService

import com.lytredrock.model.research.musicdata.ArtistResult
import com.lytredrock.model.research.musicdata.MVData
import com.lytredrock.model.research.musicdata.MVResult
import com.lytredrock.model.research.musicdata.SearchArtistData

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