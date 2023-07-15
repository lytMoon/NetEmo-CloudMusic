package com.lytredrock.lib.network.apiService

import com.lytredrock.lib.network.musicData.Comment
import com.lytredrock.lib.network.musicData.MusicComment
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ：数据接收类
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/14 17:39
 * version: 1.0
 */
interface ApiService {
     //   示例：comment/music?id=186016&limit=1
     @GET("comment/music")
     fun getMusicComments(@Query("id") id:Int,@Query("limit")limit: Int =100): Observable<MusicComment<Comment>>


}