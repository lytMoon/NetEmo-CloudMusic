package com.lytredrock.model.player.playerData

/**
 * description ：这里用来储存我们音乐播放的 时长有关信息，
 * 这里放三个参数:当前播放的位置，当前缓冲的位置，音乐的周期（一首歌的播放时长）
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/24 17:07
 * version: 1.0
 */
data class MusicProgressData( val currentPosition : Int, val bufferedPosition : Int, val duration : Int)

