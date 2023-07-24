package com.lytredrock.emocloudmusic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.HotSongListData
import com.lytredrock.emocloudmusic.data.SingSongData
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/22 16:21
 */
class HotSongListViewModel:ViewModel() {
    val hotSongListLifeData by lazy {
        MutableLiveData<List<HotSongListData.Playlists>>()
    }

    fun getHotSongListInInternet() {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(HotSongListInterface::class.java)
        testService.getInternetData1().enqueue(object : Callback<HotSongListData> {
            override fun onResponse(
                call: retrofit2.Call<HotSongListData>, response: Response<HotSongListData>
            ) {
                val data = response.body()
                if (data != null) {
                 hotSongListLifeData.postValue(data.playlists)
                }
            }
            override fun onFailure(call: retrofit2.Call<HotSongListData>, t: Throwable) {
            }
        })
    }
}