package com.lytredrock.lib.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lytredrock.lib.network.musicData.Comment
import com.lytredrock.lib.network.musicData.MusicComment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * description ：在ViewModel中封装了网络请求的具体逻辑
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/14 17:28
 * version: 1.0
 */
class ViewModel:androidx.lifecycle.ViewModel() {

    val apiService= ServiceCreator.create(ApiService::class.java)

    private val _musicComments: MutableLiveData<List<Comment>> = MutableLiveData()
    val musicComments: LiveData<List<Comment>>
        get() = _musicComments

    /***
     * 获取音乐信息的评论，这里接受一个id参数，（指定了评论获取100条，如果影响效果后面再改）
     */
    @SuppressLint("CheckResult")
    fun receiveMusicComments(id:Int){
       apiService.getMusicComments(id)
        /***
         * 下面使用了Rxjava
         */
           .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
           .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
           .subscribe(object : Observer<MusicComment<Comment>> {
               override fun onSubscribe(d: Disposable) {

               }

               override fun onError(e: Throwable) {
                   Log.d("receiveMusicComments","(ViewModel.kt:69)-->> "+e.message);
               }

               override fun onComplete() {

               }

               override fun onNext(t: MusicComment<Comment>) {
                   _musicComments.postValue(t.comments)
                   val list =t.comments
                   if (list != null) {
                       for (it in list)
                           Log.d("receiveMusicComments","(ViewModel.kt:45)-->> "+it.content)
                   }
               }

           })

   }
}