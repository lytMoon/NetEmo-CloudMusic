package com.lytredrock.emocloudmusic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.Data
import com.lytredrock.emocloudmusic.data.RecommendSongListData
import com.lytredrock.emocloudmusic.data.Song
import com.lytredrock.emocloudmusic.data.SongListData
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/19 16:48
 */
class SongListViewModel:ViewModel() {
    val songLifeData by lazy {
        MutableLiveData<List<Song>>()
    }


    fun getSongListInInternet(id:Long) {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(SongListInterface::class.java)
        testService.getInternetData1(id).enqueue(object : Callback<SongListData> {
            override fun onResponse(
                call: retrofit2.Call<SongListData>, response: Response<SongListData>
            ) {
                val data = response.body()
                if (data != null) {
                    Log.d("KKKKK", "onResponse:${data.songs[2].id} ")
                }
                if (data != null) {
                    songLifeData.postValue(data.songs)
                }
            }
            override fun onFailure(call: retrofit2.Call<SongListData>, t: Throwable) {
            }
        })
    }

}