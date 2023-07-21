package com.lytredrock.emocloudmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.Artist
import com.lytredrock.emocloudmusic.data.HotSingerData
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
 * date : 2023/7/21 17:42
 */
class HotSingerViewModel: ViewModel() {
    val hotSingerLifeData by lazy {
        MutableLiveData<List<Artist>>()
    }


    fun getHotSingerInInternet() {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(HotSingerInterface::class.java)
        testService.getInternetData1().enqueue(object : Callback<HotSingerData> {
            override fun onResponse(
                call: retrofit2.Call<HotSingerData>, response: Response<HotSingerData>
            ) {
                val data = response.body()
                if (data != null) {
                    hotSingerLifeData.postValue(data.artists)
                }
            }
            override fun onFailure(call: retrofit2.Call<HotSingerData>, t: Throwable) {
            }
        })
    }


}