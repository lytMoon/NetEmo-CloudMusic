package com.lytredrock.emocloudmusic.viewmodel

import com.lytredrock.emocloudmusic.data.FindData
import com.lytredrock.emocloudmusic.data.SongListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/19 17:00
 */
interface SongListInterface {
    @GET("playlist/track/all")
    fun getInternetData1(@Query("id")id:Long,@Query("limit")limit:Int=50): Call<SongListData>

}