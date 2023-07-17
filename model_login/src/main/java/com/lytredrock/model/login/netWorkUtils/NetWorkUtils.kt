package com.lytredrock.model.login.netWorkUtils

import android.annotation.SuppressLint
import android.util.Log
import com.lytredrock.model.login.apiService.ApiService
import com.lytredrock.model.login.apiService.IVerifyCodeInfo
import com.lytredrock.model.login.apiService.MusicInfoCallBack
import com.lytredrock.model.login.apiService.PhoneNumCallBack
import com.lytredrock.model.login.apiService.qrCallBack
import com.lytredrock.model.login.loginData.CodeData
import com.lytredrock.model.login.loginData.CodeNum
import com.lytredrock.model.login.loginData.Comment
import com.lytredrock.model.login.loginData.MusicComment
import com.lytredrock.model.login.loginData.QRData
import com.lytredrock.model.login.loginData.QRKey
import com.lytredrock.model.login.loginData.QRLast
import com.lytredrock.model.login.loginData.QRPic
import com.lytredrock.model.login.loginData.QRPicData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers.*
import io.reactivex.disposables.Disposable

import io.reactivex.schedulers.Schedulers
import com.lytredrock.model.login.apiService.PhoneNumCallBack as PhoneNumCallBack1

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
           .observeOn(mainThread())//在安卓主线程（执行onNext的逻辑）
           .subscribe(object : Observer<MusicComment<Comment>> {
               override fun onSubscribe(d: Disposable) {

               }

               override fun onError(e: Throwable) {
                   Log.d("receiveMusicComments", "(BaseViewModel.kt:69)-->> " + e.message);
               }

               override fun onComplete() {

               }

               override fun onNext(t: MusicComment<Comment>) {
//                   _musicComments.postValue(t.comments)
                   val list =t.comments
                   for (it in list)
                       Log.d("receiveMusicComments", "(BaseViewModel.kt:45)-->> " + it.content)
               }

           })
   }

    /**
     * 发送请求，得到二维码的key值
     */
    fun ReceiveQRKey(){
        apiService.qrKeyGet()
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<QRKey<QRData>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.d("ReceiveQRKey", "(BaseViewModel.kt:82)-->> ${e.message}");
                }

                override fun onComplete() {
                }

                override fun onNext(t: QRKey<QRData>) {

                   receivedNumber= t.data.unikey
                    Log.d("ReceiveQRKey", "(BaseViewModel.kt:90)-->> $receivedNumber")
                }

            })

    }

    /**
     * 得到图片相关信息
     */

    fun receiveQRPic(key:String,callBack: MusicInfoCallBack){
        apiService.qrCreat(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object: Observer<QRPic<QRPicData>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())
                }

                override fun onComplete() {

                }

                override fun onNext(t: QRPic<QRPicData>) {
                    callBack.onRespond(t.data.qrimg)
                    Log.d("receiveQRPic", "(NetWorkUtils.kt:125)-->> " + t.data.qrimg);

                }

            })

    }

    /**
     * 验证二维码扫码状态
     */
    fun receiveQRState(key:String,callBack: qrCallBack){
        apiService.qrLogin(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object:Observer<QRLast>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())
                }

                override fun onComplete() {

                }

                override fun onNext(t: QRLast) {
                    when(t.code){
                        800-> callBack.onFailed("二维码过期")
                        801-> callBack.onFailed("等待扫码")
                        802-> callBack.onFailed("待确认")
                        803-> callBack.onRespond(t)
                    }

                }

            })

    }


    /**
     * 获得手机验证码
     */

    fun receiveCodeNum(phoneNum: String, callBack: PhoneNumCallBack1){
        apiService.codeSend(phoneNum)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<CodeNum> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())
                    Log.d("receiveCodeNum","(NetWorkUtils.kt:151)-->>${e.message} ");
                }

                override fun onComplete() {

                }

                override fun onNext(t: CodeNum) {
                    callBack.onRespond(t)
                    Log.d("receiveCodeNum", "(NetWorkUtils.kt:160)-->> " + t.data + t.data);
                }

            })
    }

    /**
     * 验证验证码
     */
    fun receiveCodeState(phone:String,captcha:String,callBack: IVerifyCodeInfo){

        apiService.codeIdentify(phone,captcha)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<CodeData>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())
                }

                override fun onComplete() {
                }

                override fun onNext(t: CodeData) {
     Log.d("receiveCodeState","(NetWorkUtils.kt:184)-->> ${t.code},${t.data},${t.message}");
                  callBack.onRespond(t.data)//这里的data是true或者false类型的
                }


            })


    }





}