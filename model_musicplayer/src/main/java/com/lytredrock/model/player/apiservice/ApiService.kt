package com.lytredrock.model.player.apiservice

import com.lytredrock.model.player.playerData.Data
import com.lytredrock.model.player.playerData.HotComment
import com.lytredrock.model.player.playerData.Lrc
import com.lytredrock.model.player.playerData.MusicCommentsData
import com.lytredrock.model.player.playerData.MusicLyricsData
import com.lytredrock.model.player.playerData.MusicPlayInfoData
import com.lytredrock.model.player.playerData.Song
import com.lytredrock.model.player.playerData.UrlData
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
     * 返回音乐的播放链接（.MP3格式）,设置超清母带（测试中）
     */
    @GET("song/url/v1")
    fun getMusicUrl(@Query("id")id:String,@Query("level")level:String="jymaster"): Observable<UrlData<Data>>


    /**
     * 返回音乐的相关信息
     */
    @GET("song/detail")
    fun getMusicInfo(@Query("ids")ids:String):Observable<MusicPlayInfoData<Song>>

    /**
     * 返回音乐的歌词
     */
    @GET("lyric")
    fun getMusicLyrics(@Query("id")id:String):Observable<MusicLyricsData<Lrc>>
    /**
     * 返回歌曲的评论
     */
    @GET("comment/music")
    fun getComments(@Query("id")id:String,@Query("limit")limit:Int=500):Observable<MusicCommentsData<HotComment>>
}


