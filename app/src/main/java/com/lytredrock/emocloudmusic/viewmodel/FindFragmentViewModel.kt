package com.lytredrock.emocloudmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.Banner
import com.lytredrock.emocloudmusic.data.BannerData
import com.lytredrock.emocloudmusic.data.Data
import com.lytredrock.emocloudmusic.data.FindData
import com.lytredrock.emocloudmusic.data.RecommendSongListData
import com.lytredrock.emocloudmusic.data.Result
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/17 14:42
 */
class FindFragmentViewModel : ViewModel() {

    val dataLifeData by lazy {
        MutableLiveData<List<Data>>()
    }
    val bannerLifeData by lazy {
        MutableLiveData<List<Banner>>()
    }
    val recommendSongListLifeData by lazy {
        MutableLiveData<List<Result>>()
    }

    fun getBallInFragment() {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(FindDataInterface::class.java)
        testService.getInternetData1().enqueue(object : Callback<FindData> {
            override fun onResponse(call: retrofit2.Call<FindData>, response: Response<FindData>) {
                val data = response.body()
                if (data != null) {
                    dataLifeData.postValue(data.data)
                }
            }

            override fun onFailure(call: retrofit2.Call<FindData>, t: Throwable) {
            }
        })
    }

    fun getBannerInFragment(type: Int) {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(FindDataInterface::class.java)
        testService.getInternetData2(type).enqueue(object : Callback<BannerData> {
            override fun onResponse(
                call: retrofit2.Call<BannerData>, response: Response<BannerData>
            ) {
                val data = response.body()
                if (data != null) {
                    bannerLifeData.postValue(data.banners)
                }
            }

            override fun onFailure(call: retrofit2.Call<BannerData>, t: Throwable) {
            }
        })
    }

    fun getRecommendSongLIstInFragment() {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(FindDataInterface::class.java)
        testService.getInternetData3().enqueue(object : Callback<RecommendSongListData> {
            override fun onResponse(
                call: retrofit2.Call<RecommendSongListData>,
                response: Response<RecommendSongListData>
            ) {
                val data = response.body()
                if (data != null) {
                    recommendSongListLifeData.postValue(data.result)
                }
            }

            override fun onFailure(call: retrofit2.Call<RecommendSongListData>, t: Throwable) {
            }
        })
    }

}