package com.lytredrock.emocloudmusic.data

data class DownloadData(
    val `data`: List<Data>
) {
    data class Data(
        val url: String,
    )
}