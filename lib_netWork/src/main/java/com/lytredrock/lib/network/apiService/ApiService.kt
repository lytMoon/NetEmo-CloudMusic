package com.lytredrock.lib.network.apiService

import com.lytredrock.lib.network.musicData.Comment
import com.lytredrock.lib.network.musicData.MusicComment
import com.lytredrock.lib.network.musicData.QRData
import com.lytredrock.lib.network.musicData.QRKey
import com.lytredrock.lib.network.musicData.QRLast
import com.lytredrock.lib.network.musicData.QRPic
import com.lytredrock.lib.network.musicData.QRPicData
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
     /***
      * 获取音乐评论
      */
     @GET("comment/music")
     fun getMusicComments(@Query("id") id:Int,@Query("limit")limit: Int =100): Observable<MusicComment<Comment>>

     /***
      * 二维码key生成
      */
     @GET("login/qr/key")
     fun qrKeyGet():Observable<QRKey<QRData>>

     /**
      * 二维码生成
      */
     @GET("login/qr/create")
     fun qrCreat(@Query("key") key:String,@Query("qrimg") qrimg:Int=200):Observable<QRPic<QRPicData>>

     /**
      * 二维码查询（获取cookie）
      */
     @GET("login/qr/check")
     fun qeLogin(@Query("key") key:String):Observable<QRLast>
}