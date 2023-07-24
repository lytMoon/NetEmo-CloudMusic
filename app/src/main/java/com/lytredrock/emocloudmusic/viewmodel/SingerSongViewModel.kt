package com.lytredrock.emocloudmusic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.SingSongData
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
 * date : 2023/7/22 11:16
 */
class SingerSongViewModel:ViewModel() {
    val singerSongLifeData by lazy {
        MutableLiveData<List<SingSongData.SingerSong>>()
    }


    fun getSingerSongInInternet(id:Int) {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(SingerSongInterface::class.java)
        testService.getInternetData1(id).enqueue(object : Callback<SingSongData> {
            override fun onResponse(
                call: retrofit2.Call<SingSongData>, response: Response<SingSongData>
            ) {
                val data = response.body()
                if (data != null) {
                    Log.d("BBBBB", "onResponse:${data.songs} ")
                  singerSongLifeData.postValue(data.songs)
                }
            }
            override fun onFailure(call: retrofit2.Call<SingSongData>, t: Throwable) {
            }
        })
    }

}