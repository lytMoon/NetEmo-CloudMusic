package com.lytredrock.lib.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lytredrock.lib.network.musicData.Comment
import com.lytredrock.lib.network.musicData.MusicComment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ：在ViewModel中封装了网络请求的具体逻辑
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/14 17:28
 * version: 1.0
 */
class ViewModel {
    //把retrofit对象和apiService对象的构造先提取出来
    val retrofit = Retrofit.Builder()
        .baseUrl("http://why.vin:2023/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
    val apiService= retrofit.create(ApiService::class.java)

    private val _musicComments: MutableLiveData<List<Comment>> = MutableLiveData()
    val musicComments: LiveData<List<Comment>>
        get() = _musicComments

    /***
     * 获取音乐信息的评论，这里接受一个id参数，（指定了评论获取50条，如果影响效果后面再改）
     */
    @SuppressLint("CheckResult")
    fun receiveMusicComments(id:String){
       apiService.getMusicComments(id)
           .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
           .observeOn(AndroidSchedulers.mainThread())//在安卓主线程更新UI
           .subscribe(object : Observer<MusicComment<Comment>>{
               override fun onSubscribe(d: Disposable) {
               }

               override fun onError(e: Throwable) {
               }

               override fun onComplete() {
               }

               override fun onNext(t: MusicComment<Comment>) {
                   _musicComments.postValue(t.comments)
                   Log.d("receiveMusicComments", "(ViewModel.kt:57)-->> $_musicComments");


               }

           })

   }



}