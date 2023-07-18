package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.BannerData
import com.lytredrock.emocloudmusic.data.FindData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/18 10:44
 */
interface FindDataInterface {
    @GET("homepage/dragon/ball")
    fun getInternetData1(): Call<FindData>


    @GET("banner")
    fun getInternetData2(@Query("type")type: Int ): Call<BannerData>
}