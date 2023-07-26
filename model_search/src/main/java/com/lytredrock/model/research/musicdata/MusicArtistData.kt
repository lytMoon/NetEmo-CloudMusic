package com.lytredrock.model.research.musicdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/26 21:14
 * version: 1.0
 */
data class MusicArtistData<T>(
    val code: Int,
    val more: Boolean,
    val songs: List<ArtistSong>
)

data class ArtistSong(
    val al: Al,
    val ar: List<Ar>,
    val id: Int,
    val name: String,
    val mv: Int,
)

data class Al(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
)

data class Ar(
    val alias: List<Any>,
    val id: Int,
    val name: String,
    val tns: List<Any>
)

