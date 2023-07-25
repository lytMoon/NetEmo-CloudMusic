package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.DownloadData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 10:34
 */
interface DownloadInterface {
    @GET("song/url/v1")
    fun getInternetData1(@Query("id") id: Int,@Query("level") level:String): Call<DownloadData>


}