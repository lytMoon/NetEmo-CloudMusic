//package com.lytredrock.model.research.musicdata
//
///**
// * description ：
// * author : lytMoon
// * email : yytds@foxmail.com
// * date : 2023/7/18 18:34
// * version: 1.0
// */
//data class Test(
//    val code: Int,
//    val result: Result
//)
//
//data class Result(
//    val hasMore: Boolean,
//    val songCount: Int,
//    val songs: List<Song>
//)
//
//data class Song(
//    val album: Album,
//    val alias: List<String>,
//    val artists: List<ArtistX>,
//    val copyrightId: Int,
//    val duration: Int,
//    val fee: Int,
//    val ftype: Int,
//    val id: Int,
//    val mark: Int,
//    val mvid: Int,
//    val name: String,
//    val rUrl: Any,
//    val rtype: Int,
//    val status: Int
//)
//
//data class Album(
//    val artist: ArtistX,
//    val copyrightId: Int,
//    val id: Int,
//    val mark: Int,
//    val name: String,
//    val picId: Long,
//    val publishTime: Long,
//    val size: Int,
//    val status: Int
//)
//
//data class ArtistX(
//    val albumSize: Int,
//    val alias: List<Any>,
//    val fansGroup: Any,
//    val id: Int,
//    val img1v1: Int,
//    val img1v1Url: String,
//    val name: String,
//    val picId: Int,
//    val picUrl: Any,
//    val trans: Any
//)