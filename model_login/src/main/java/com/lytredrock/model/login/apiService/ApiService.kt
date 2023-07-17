package com.lytredrock.model.login.apiService


import com.lytredrock.model.login.loginData.CodeData
import com.lytredrock.model.login.loginData.CodeNum
import com.lytredrock.model.login.loginData.Comment
import com.lytredrock.model.login.loginData.MusicComment
import com.lytredrock.model.login.loginData.QRData
import com.lytredrock.model.login.loginData.QRKey
import com.lytredrock.model.login.loginData.QRLast
import com.lytredrock.model.login.loginData.QRPic
import com.lytredrock.model.login.loginData.QRPicData
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
      * 二维码查询（检查登录状态）
      */
     @GET("login/qr/check")
     fun qrLogin(@Query("key") key:String):Observable<QRLast>


     /**
      * 发送验证码
      */
     @GET("captcha/sent")
     fun codeSend(@Query("phone") phone: String) :Observable<CodeNum>
     /**
      * 验证验证码
      */
     @GET("captcha/verify")
     fun codeIdentify(@Query("phone")phone:String,@Query("captcha")captcha:String):Observable<CodeData>
}