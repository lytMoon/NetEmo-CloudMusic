package com.lytredrock.lib.network.netWorkUtils

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lytredrock.lib.network.apiService.ApiService
import com.lytredrock.lib.network.apiService.MusicInfoCallBack
import com.lytredrock.lib.network.musicData.Comment
import com.lytredrock.lib.network.musicData.MusicComment
import com.lytredrock.lib.network.musicData.QRData
import com.lytredrock.lib.network.musicData.QRKey
import com.lytredrock.lib.network.musicData.QRPic
import com.lytredrock.lib.network.musicData.QRPicData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * description ：把网络请求的具体逻辑封装成一个单例类
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/14 17:28
 * version: 1.0
 */
object NetWorkUtils {
    var receivedNumber: String? = null
    var qrUrl:String ? = null


    private val apiService= ServiceCreatorUtils.create(ApiService::class.java)

//    //  得到音乐评论的liveData
//    private val _musicComments: MutableLiveData<List<Comment>> = MutableLiveData()
//    val musicComments: LiveData<List<Comment>>
//        get() = _musicComments



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
                   Log.d("receiveMusicComments","(BaseViewModel.kt:69)-->> "+e.message);
               }

               override fun onComplete() {

               }

               override fun onNext(t: MusicComment<Comment>) {
//                   _musicComments.postValue(t.comments)
                   val list =t.comments
                   for (it in list)
                       Log.d("receiveMusicComments","(BaseViewModel.kt:45)-->> "+it.content)
               }

           })
   }

    /**
     * 发送请求，得到二维码的key值
     */
    fun ReceiveQRKey(){
        apiService.qrKeyGet()
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<QRKey<QRData>>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.d("ReceiveQRKey","(BaseViewModel.kt:82)-->> ${e.message}");
                }

                override fun onComplete() {
                }

                override fun onNext(t: QRKey<QRData>) {
                   receivedNumber= t.data.unikey
                    Log.d("ReceiveQRKey","(BaseViewModel.kt:90)-->> "+receivedNumber)
                }

            })

    }

    /**
     * 得到图片相关信息
     */

    fun receiveQRPic(key:String,callBack: MusicInfoCallBack){
        apiService.qrCreat(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object:Observer<QRPic<QRPicData>>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())
                }

                override fun onComplete() {

                }

                override fun onNext(t: QRPic<QRPicData>) {
                    callBack.onRespond(t.data.qrimg)
                    Log.d("receiveQRPic", "(NetWorkUtils.kt:125)-->> "+t.data.qrimg);

                }

            })

    }



}