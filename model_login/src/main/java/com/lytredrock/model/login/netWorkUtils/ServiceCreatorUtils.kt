package com.lytredrock.model.login.netWorkUtils

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/14 21:47
 * version: 1.0
 */
 object  ServiceCreatorUtils {
    private const val BASE_URL ="http://why.vin:2023/"
    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>):T = retrofit.create(serviceClass)
}