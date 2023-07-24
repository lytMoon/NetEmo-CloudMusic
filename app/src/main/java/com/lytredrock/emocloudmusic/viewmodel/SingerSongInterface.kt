package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.SingSongData
import com.lytredrock.emocloudmusic.data.SongChartData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/22 11:19
 */
interface SingerSongInterface {

    @GET("artist/songs")
    fun getInternetData1(@Query("id")id:Int): Call<SingSongData>

}