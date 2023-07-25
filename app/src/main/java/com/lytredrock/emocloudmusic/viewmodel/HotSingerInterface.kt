package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.HotSingerData
import retrofit2.Call
import retrofit2.http.GET

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/21 17:44
 */
interface HotSingerInterface {
    @GET("top/artists")
    fun getInternetData1(): Call<HotSingerData>

}