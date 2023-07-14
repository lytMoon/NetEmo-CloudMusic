package com.lytredrock.lib.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lytredrock.lib.network.musicData.Comment
import com.lytredrock.lib.network.musicData.MusicComment

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
           .enqueue(object: Callback<MusicComment<Comment>>{
               override fun onResponse(
                   call: Call<MusicComment<Comment>>,
                   response: Response<MusicComment<Comment>>

               ) {
                   _musicComments.postValue(response.body()?.comments)
                   val list = response.body()?.comments
                   if (list != null) {
                       for (it in list)
                           Log.d("receiveMusicComments","(ViewModel.kt:45)-->> "+it.content)
                   };

               }

               override fun onFailure(call: Call<MusicComment<Comment>>, t: Throwable) {
                  Log.d("receiveMusicComments", "(ViewModel.kt:57)-->> 失败了");
               }

           })
//           .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
//           .observeOn(AndroidSchedulers.mainThread())//在安卓主线程更新UI（执行onNext的逻辑）
//           .subscribe(object : Observer<MusicComment<Comment>> {
//               override fun onSubscribe(d: Disposable) {
//               }
//
//               override fun onError(e: Throwable) {
//               }
//
//               override fun onComplete() {
//               }
//
//               override fun onNext(t: MusicComment<Comment>) {
//                   _musicComments.postValue(t.comments)
//                   Log.d("receiveMusicComments", "(ViewModel.kt:57)-->> $_musicComments");
//
//
//               }
//
//           })

   }



}