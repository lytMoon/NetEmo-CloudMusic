package com.lytredrock.emocloudmusic.data

data class SingSongData(
    val songs: List<SingerSong>,
) {
    data class SingerSong(

        val al: Al,

        val ar: List<Ar>,

        val id: Int,

        val mv: Int,

        val name: String,




    ) {
        data class Al(
            val name: String,
        )

        data class Ar(
            val id: Int,
            val name: String
        )




    }
}