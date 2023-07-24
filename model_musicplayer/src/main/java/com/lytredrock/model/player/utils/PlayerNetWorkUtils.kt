package com.lytredrock.model.player.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lytredrock.model.player.apiservice.ApiService
import com.lytredrock.model.player.playerData.Data
import com.lytredrock.model.player.playerData.HotComment
import com.lytredrock.model.player.playerData.Lrc
import com.lytredrock.model.player.playerData.MusicCommentsData
import com.lytredrock.model.player.playerData.MusicLyricsData
import com.lytredrock.model.player.playerData.MusicPlayInfoData
import com.lytredrock.model.player.playerData.Song
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


    /**
     * 获取音乐的播放链接
     */
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

    /**
     * 获取音乐的相关信息
     */
    fun receiveMusicInfo(key: String, _musicInfo: MutableLiveData<List<Song>>) {
        apiService.getMusicInfo(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MusicPlayInfoData<Song>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d(
                        "receiveMusicInfo",
                        "(PlayerNetWorkUtils.kt:72)-->> ${e.message.toString()}"
                    );
                }

                override fun onComplete() {
                }

                override fun onNext(t: MusicPlayInfoData<Song>) {
                    _musicInfo.postValue(t.songs)
                    Log.d("receiveMusicInfo111", "(PlayerNetWorkUtils.kt:80)-->> ${t.songs}");
                }

            })
    }

    /**
     * 获取音乐的歌词
     */
    fun receiveLyrics(key:String,_musicLyricsInfo: MutableLiveData<List<Lrc>>){
        apiService.getMusicLyrics(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object:Observer<MusicLyricsData<Lrc>>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

                override fun onNext(t: MusicLyricsData<Lrc>) {
                    _musicLyricsInfo.postValue(listOf(t.lrc))
                }

            })

    }
    /**
     * 获取mv的评论
     */

    fun receiveMusicComments(key: String, _musicCommentsData: MutableLiveData<List<HotComment>>) {
        apiService.getComments(key)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MusicCommentsData<HotComment>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("receiveMvComments", "(MvNetWorkUtil.kt:99)-->> ${e.message.toString()}");
                }

                override fun onComplete() {
                }

                override fun onNext(t: MusicCommentsData<HotComment>) {
                    _musicCommentsData.postValue(t.hotComments)
                    Log.d("receiveMvComments", "(MvNetWorkUtil.kt:105)-->> ${t.hotComments}");
                }

            })

    }


}