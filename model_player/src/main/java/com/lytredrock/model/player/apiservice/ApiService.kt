package com.lytredrock.model.player.apiservice

import com.lytredrock.model.player.playerData.Data
import com.lytredrock.model.player.playerData.UrlData
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
     * 返回音乐的播放链接（.MP3格式）
     */
    @GET("song/url/v1")
    fun getMusicUrl(@Query("id")id:String,@Query("level")level:String="exhigh"): Observable<UrlData<Data>>




}