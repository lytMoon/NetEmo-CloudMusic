package com.lytredrock.model.research.apisearch

import com.lytredrock.model.research.musicdata.ArtistResult
import com.lytredrock.model.research.musicdata.SearchArtistData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 12:01
 * version: 1.0
 */
interface ArtistCallBack {
    fun onRespond(t: SearchArtistData<ArtistResult>)
    fun onFailed(e: String)
}