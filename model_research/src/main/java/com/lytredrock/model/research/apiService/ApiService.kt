package com.lytredrock.model.research.apiService

import com.lytredrock.model.research.musicdata.Result
import com.lytredrock.model.research.musicdata.SearchSongData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ：用于搜索功能的实现
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 20:31
 * version: 1.0
 */
interface ApiService {

    /**
     * 返回搜索的单曲的结果
     */
    @GET("search")
    fun getSongInfo(@Query("keywords")keywords:String): Observable<SearchSongData<Result>>


}