package com.lytredrock.model.research.internetUtils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lytredrock.model.research.apisearch.ApiService
import com.lytredrock.model.research.musicdata.Artist
import com.lytredrock.model.research.musicdata.ArtistResult
import com.lytredrock.model.research.musicdata.MVData
import com.lytredrock.model.research.musicdata.MVResult
import com.lytredrock.model.research.musicdata.Mv
import com.lytredrock.model.research.musicdata.Result
import com.lytredrock.model.research.musicdata.SearchArtistData
import com.lytredrock.model.research.musicdata.SearchSongData
import com.lytredrock.model.research.musicdata.Song
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
object SearchNetWorkUtil {

    private val apiService = ServiceCreatorUtils.create(ApiService::class.java)


    /**
     * 返回搜索音乐单曲的结果
     */
    fun receiveSongInfo(keywords: String, _songData: MutableLiveData<List<Song>>) {
        Log.d("receiveSongInfo", "(SearchNetWorkUtil.kt:36)-->> $keywords");
        apiService.getSongInfo(keywords)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<SearchSongData<Result>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d(
                        "receiveSongInfo",
                        "(SearchNetWorkUtil.kt:47)-->>请求失败了 ${e.message.toString()}"
                    );
                }

                override fun onComplete() {
                }

                override fun onNext(t: SearchSongData<Result>) {
                    _songData.postValue(t.result.songs)
                    Log.d(
                        "receiveSongInfo",
                        "(SearchNetWorkUtil.kt:58)-->> " + t.result.songs.toString()
                    );
                }


            }
            )


    }

    /**
     * 返回搜索音乐歌手的结果（默认三十条）
     */

    fun receiveArtistsInfo(keywords: String, _artistData: MutableLiveData<List<Artist>>) {

        apiService.getArtistInfo(keywords)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<SearchArtistData<ArtistResult>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("SearchArtistData", "(SearchNetWorkUtil.kt:75)-->> ${e.message}");
                }

                override fun onComplete() {
                }

                override fun onNext(t: SearchArtistData<ArtistResult>) {
                    _artistData.postValue(t.result.artists)
                    Log.d("SearchArtistData", "(SearchNetWorkUtil.kt:81)-->> ${t.result.artists}");
                }

            })
    }


    /**
     * 返回搜索的mv信息
     */

    fun receiveMVInfo(keywords: String, _mvData: MutableLiveData<List<Mv>>) {
        apiService.getMVInfo(keywords)
            .subscribeOn(Schedulers.newThread())//新开一个线程进行请求
            .observeOn(AndroidSchedulers.mainThread())//在安卓主线程（执行onNext的逻辑）
            .subscribe(object : Observer<MVData<MVResult>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                }

                override fun onNext(t: MVData<MVResult>) {
                    _mvData.postValue(t.result.mvs)
                    Log.d("receiveMVInfo", "(SearchNetWorkUtil.kt:114)-->> ${t.result.mvs}");
                }

            })

    }

}