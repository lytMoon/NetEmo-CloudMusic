package com.lytredrock.model.research.musicdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/18 14:24
 * version: 1.0
 */
 data class SearchSongData<T>(
    val code: Int,
    val result: Result
)

data class Result(
    val hasMore: Boolean,
    val songCount: Int,
    val songs: List<Song>
)

data class Song(
    val artists: List<ArtistX>,
    val id: Int,
    val name: String,
)
data class ArtistX(
    val id: Int,
    val img1v1: Int,
    val img1v1Url: String,
    val name: String,
    val picId: Int,
    val picUrl: Any,
    val trans: Any
)