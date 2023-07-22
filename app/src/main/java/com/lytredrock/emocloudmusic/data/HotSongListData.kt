package com.lytredrock.emocloudmusic.data

data class HotSongListData(
    val playlists: List<Playlists>,
) {
    data class Playlists(

        val coverImgUrl: String,

        val id: Long,

        val name: String,

    )

}