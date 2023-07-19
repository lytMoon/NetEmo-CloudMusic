package com.lytredrock.model.research.musicdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 11:45
 * version: 1.0
 */
data class SearchArtistData<T>(
    val code: Int,
    val result: ArtistResult
)

data class ArtistResult(
    val artists: List<Artist>,
)

data class Artist(
    val id: Int,
    val img1v1Url: String,
    val name: String,
)