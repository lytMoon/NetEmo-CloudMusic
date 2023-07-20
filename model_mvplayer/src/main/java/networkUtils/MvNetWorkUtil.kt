package networkUtils

import android.util.Log
import apiService.ApiService
import apiService.MvInfoCallBack
import apiService.MvUrlCallBack
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mvdata.Data
import mvdata.MvInfoData
import mvdata.MvUrlData
import mvdata.UrlData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 17:05
 * version: 1.0
 */
object MvNetWorkUtil {

    private val apiService= ServiceCreatorUtils.create(ApiService::class.java)


    /**
     * 获取mv的相关信息
     */
    fun receiveMvInfo(key:String,callBack:MvInfoCallBack){
        apiService.getMvInfo(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object :Observer<MvInfoData<Data>>{
                override fun onSubscribe(d: Disposable) {
                }
                override fun onError(e: Throwable) {
                    Log.d("receiveMvInfo","(MvNetWorkUtil.kt:36)-->> ${e.message}");
                    callBack.onFailed(e.message.toString())
                }
                override fun onComplete() {
                }
                override fun onNext(t: MvInfoData<Data>) {
                    callBack.onRespond(t)
                    Log.d("receiveMvInfo","(MvNetWorkUtil.kt:43)-->> ${t.data}");
                }

            })
    }

    /**
     * 获取mv的播放链接
     */

    fun receiveMvUrlInfo(key: String, callBack: MvUrlCallBack){
        apiService.getMvUrlInfo(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object :Observer<MvUrlData<UrlData>>{
                override fun onSubscribe(d: Disposable) {
                }
                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())

                }
                override fun onComplete() {
                }
                override fun onNext(t: MvUrlData<UrlData>) {
                 callBack.onRespond(t)
                 Log.d("receiveMvUrlInfo","(MvNetWorkUtil.kt:73)-->> ${t.data.url}");


                }
            })
    }

}