package com.lytredrock.model.player.playerData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/22 16:03
 * version: 1.0
 */
data class MusicPlayInfoData<T>(
    val code: Int,
    val songs: List<Song>
)


data class Song(

    val al: Al,
    val ar: List<Ar>,
    val id: Int,
    val name: String,
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
