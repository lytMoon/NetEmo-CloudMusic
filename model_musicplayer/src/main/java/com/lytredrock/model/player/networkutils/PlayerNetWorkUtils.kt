package com.lytredrock.model.player.networkutils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lytredrock.model.player.apiservice.ApiService
import com.lytredrock.model.player.playerData.Data
import com.lytredrock.model.player.playerData.UrlData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 20:28
 * version: 1.0
 */
object PlayerNetWorkUtils {

    private val apiService = ServiceCreatorUtils.create(ApiService::class.java)


    fun getUrl(keyword: String, _musicInfo: MutableLiveData<List<Data>>) {

        apiService.getMusicUrl(keyword)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<UrlData<Data>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: UrlData<Data>) {
                    _musicInfo.postValue(t.data)
                    Log.d("getUrl", "(PlayerNetWorkUtils.kt:44)-->> ${t.data}")

                }


            })


    }


}