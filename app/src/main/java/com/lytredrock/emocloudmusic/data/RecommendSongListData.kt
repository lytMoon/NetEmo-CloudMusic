package com.lytredrock.emocloudmusic.data

data class RecommendSongListData(
    val category: Int,
    val code: Int,
    val hasTaste: Boolean,
    val result: List<Result>
)