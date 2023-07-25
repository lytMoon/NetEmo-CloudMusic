package com.lytredrock.emocloudmusic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.Data
import com.lytredrock.emocloudmusic.data.DownloadData
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 10:29
 */
class DownloadViewModel:ViewModel() {
    val downloadDataLifeData by lazy {
        MutableLiveData<List<DownloadData.Data>>()
    }

    fun getDownloadDataInInternet(id:Int,level:String) {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(DownloadInterface::class.java)
        testService.getInternetData1(id,level).enqueue(object : Callback<DownloadData> {
            override fun onResponse(call: retrofit2.Call<DownloadData>, response: Response<DownloadData>) {
                val data = response.body()
                if (data != null) {
                    downloadDataLifeData.postValue(data.data)
                }
            }

            override fun onFailure(call: retrofit2.Call<DownloadData>, t: Throwable) {
            }
        })
    }
}