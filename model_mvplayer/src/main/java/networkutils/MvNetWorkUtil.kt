package networkutils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import apiservice.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mvdata.Data
import mvdata.HotComment
import mvdata.MvCommentsData
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

    private val apiService = ServiceCreatorUtils.create(ApiService::class.java)


    /**
     * 获取mv的相关信息
     */
    fun receiveMvInfo(key: String, _mvInfoData: MutableLiveData<List<Data>>) {
        apiService.getMvInfo(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MvInfoData<Data>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("receiveMvInfo", "(MvNetWorkUtil.kt:36)-->> ${e.message}");
                }

                override fun onComplete() {
                }

                override fun onNext(t: MvInfoData<Data>) {
                    _mvInfoData.postValue(listOf(t.data))
                    Log.d("receiveMvInfo", "(MvNetWorkUtil.kt:43)-->> ${t.data}");
                }

            })
    }

    /**
     * 获取mv的播放链接
     */

    fun receiveMvUrlInfo(key: String, _mvUrlData: MutableLiveData<List<UrlData>>) {
        apiService.getMvUrlInfo(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MvUrlData<UrlData>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                }

                override fun onNext(t: MvUrlData<UrlData>) {
                    _mvUrlData.postValue(listOf(t.data))
                    Log.d("receiveMvUrlInfo", "(MvNetWorkUtil.kt:73)-->> ${t.data.url}");


                }
            })
    }

    /**
     * 获取mv的评论
     */

    fun receiveMvComments(key: String, _mvCommentsData: MutableLiveData<List<HotComment>>) {
        apiService.getComments(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MvCommentsData<HotComment>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("receiveMvComments", "(MvNetWorkUtil.kt:99)-->> ${e.message.toString()}");
                }

                override fun onComplete() {
                }

                override fun onNext(t: MvCommentsData<HotComment>) {
                    _mvCommentsData.postValue(t.hotComments)
                    Log.d("receiveMvComments", "(MvNetWorkUtil.kt:105)-->> ${t.hotComments}");
                }

            })

    }

}