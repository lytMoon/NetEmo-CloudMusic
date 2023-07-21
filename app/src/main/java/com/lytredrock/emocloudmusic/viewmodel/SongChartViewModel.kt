package com.lytredrock.emocloudmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.SongChart
import com.lytredrock.emocloudmusic.data.SongChartData
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/21 13:58
 */
class SongChartViewModel: ViewModel() {
    val chartLifeData by lazy {
        MutableLiveData<List<SongChart>>()
    }


    fun getSongChartInInternet() {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(SongChartInterface::class.java)
        testService.getInternetData1().enqueue(object : Callback<SongChartData> {
            override fun onResponse(
                call: retrofit2.Call<SongChartData>, response: Response<SongChartData>
            ) {
                val data = response.body()
                if (data != null) {
                    chartLifeData.postValue(data.list)
                }
            }
            override fun onFailure(call: retrofit2.Call<SongChartData>, t: Throwable) {
            }
        })
    }


}