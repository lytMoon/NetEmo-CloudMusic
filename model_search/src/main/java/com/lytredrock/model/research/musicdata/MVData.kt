package com.lytredrock.model.research.musicdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 15:40
 * version: 1.0
 */
data class MVData<T>(
    val code: Int,
    val result: MVResult
)

data class MVResult(
    val hlWords: Any,
    val mvCount: Int,
    val mvs: List<Mv>
)

data class Mv(
    val alg: String,
    val alias: List<String>,
    val arTransName: String,
    val artistId: Int,
    val artistName: String,
    val artists: List<MVArtist>,
    val briefDesc: Any,
    val cover: String,
    val desc: Any,
    val duration: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val playCount: Int,
    val transNames: List<String>
)

data class MVArtist(
    val alias: List<String>,
    val id: Int,
    val name: String,
    val transNames: List<String>
)