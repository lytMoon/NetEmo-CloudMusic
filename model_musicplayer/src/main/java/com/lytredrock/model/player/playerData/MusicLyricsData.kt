package com.lytredrock.model.player.playerData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/23 20:18
 * version: 1.0
 */
data class MusicLyricsData<T>(
    val code: Int,
    val klyric: Klyric,
    val lrc: Lrc,
    val qfy: Boolean,
    val romalrc: Romalrc,
    val sfy: Boolean,
    val sgc: Boolean,
    val tlyric: Tlyric
)

data class Klyric(
    val lyric: String,
    val version: Int
)

data class Lrc(
    val lyric: String,
    val version: Int
)

data class Romalrc(
    val lyric: String,
    val version: Int
)

data class Tlyric(
    val lyric: String,
    val version: Int
)