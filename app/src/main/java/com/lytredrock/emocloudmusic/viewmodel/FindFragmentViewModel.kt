package com.lytredrock.emocloudmusic.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.Banner
import com.lytredrock.emocloudmusic.data.BannerData
import com.lytredrock.emocloudmusic.data.Data
import com.lytredrock.emocloudmusic.data.FindData
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

    val dataLIfeData by lazy {
        MutableLiveData<List<Data>>()
    }
    val bannerLIfeData by lazy {
        MutableLiveData<List<Banner>>()
    }

    fun getBallInFragment() {
        val retrofit = Retrofit.Builder().baseUrl("http://why.vin:2023/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val testService = retrofit.create(FindDataInterface::class.java)
        testService.getInternetData1().enqueue(object : Callback<FindData> {
            override fun onResponse(call: retrofit2.Call<FindData>, response: Response<FindData>) {
                val data = response.body()
                if (data != null) {
                    dataLIfeData.postValue(data.data)
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
                    bannerLIfeData.postValue(data.banners)
                }
            }
            override fun onFailure(call: retrofit2.Call<BannerData>, t: Throwable) {
            }
        })
    }

}