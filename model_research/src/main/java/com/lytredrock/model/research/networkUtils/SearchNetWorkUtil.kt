package com.lytredrock.model.research.networkUtils

import android.util.Log
import com.lytredrock.model.research.apiService.ApiService
import com.lytredrock.model.research.apiService.ArtistCallBack
import com.lytredrock.model.research.apiService.SongCallBack
import com.lytredrock.model.research.musicdata.ArtistResult
import com.lytredrock.model.research.musicdata.Result
import com.lytredrock.model.research.musicdata.SearchArtistData
import com.lytredrock.model.research.musicdata.SearchSongData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.nio.channels.NonReadableChannelException

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 20:28
 * version: 1.0
 */
object SearchNetWorkUtil {

    private val apiService= ServiceCreatorUtils.create(ApiService::class.java)


    /**
     * 返回搜索音乐单曲的结果
     */
    fun receiveSongInfo(keywords:String,callBack: SongCallBack){
        Log.d("receiveSongInfo","(SearchNetWorkUtil.kt:29)-->> $keywords");
        apiService.getSongInfo(keywords)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object :Observer<SearchSongData<Result>>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("receiveSongInfo","(SearchNetWorkUtil.kt:38)-->>请求失败了 ${e.message.toString()}");
                    callBack.onFailed(e.message.toString())
                }

                override fun onComplete() {
                }

                override fun onNext(t: SearchSongData<Result>) {
                    callBack.onRespond(t)
                    Log.d("receiveSongInfo","(SearchNetWorkUtil.kt:42)-->> "+t.result.songs.toString());
                }


            }
              )


    }

    /**
     * 返回搜索音乐歌手的结果（默认三十条）
     */

    fun receiveArtistsInfo(keywords:String,callBack: ArtistCallBack){

        apiService.getArtistInfo(keywords)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object :Observer<SearchArtistData<ArtistResult>>{
                override fun onSubscribe(d: Disposable) {
                }
                override fun onError(e: Throwable) {
                    callBack.onFailed(e.message.toString())
                    Log.d("SearchArtistData","(SearchNetWorkUtil.kt:75)-->> ${e.message}");
                }
                override fun onComplete() {
                }
                override fun onNext(t: SearchArtistData<ArtistResult>) {
          Log.d("SearchArtistData","(SearchNetWorkUtil.kt:81)-->> ${t.result.artists}");
                     callBack.onRespond(t)
                }

            })
    }




}