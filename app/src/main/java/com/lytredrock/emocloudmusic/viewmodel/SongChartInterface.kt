package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.SongChartData
import retrofit2.Call
import retrofit2.http.GET

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/21 14:00
 */
interface SongChartInterface {

    @GET("toplist/detail")
    fun getInternetData1(): Call<SongChartData>
}