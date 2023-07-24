package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.HotSongListData
import com.lytredrock.emocloudmusic.data.SingSongData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/22 16:24
 */
interface HotSongListInterface {

    @GET("top/playlist")
    fun getInternetData1(): Call<HotSongListData>
}