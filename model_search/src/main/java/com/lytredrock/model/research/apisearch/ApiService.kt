package com.lytredrock.model.research.apisearch

import com.lytredrock.model.research.musicdata.ArtistResult
import com.lytredrock.model.research.musicdata.ArtistSong
import com.lytredrock.model.research.musicdata.MVData
import com.lytredrock.model.research.musicdata.MVResult
import com.lytredrock.model.research.musicdata.MusicArtistData
import com.lytredrock.model.research.musicdata.Result
import com.lytredrock.model.research.musicdata.SearchArtistData
import com.lytredrock.model.research.musicdata.SearchSongData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ：用于搜索功能的实现
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 20:31
 * version: 1.0
 */
interface ApiService {

    /**
     * 返回搜索的单曲的结果
     */
    @GET("search")
    fun getSongInfo(@Query("keywords") keywords: String): Observable<SearchSongData<Result>>


    /***
     * 返回搜索的歌手的结果
     */
    @GET("search")
    fun getArtistInfo(
        @Query("keywords") keywords: String,
        @Query("type") type: Int = 100
    ): Observable<SearchArtistData<ArtistResult>>


    /**
     * 返回搜索的MV结果
     */
    @GET("search")
    fun getMVInfo(
        @Query("keywords") keywords: String,
        @Query("type") type: Int = 1004
    ): Observable<MVData<MVResult>>

    /**
     * 返回歌手的热门50首歌曲
     */
    @GET("artist/top/song")
    fun getArtistMusic(@Query("id")id:String):Observable<MusicArtistData<ArtistSong>>

}