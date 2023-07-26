package com.lytredrock.model.research.searchviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.model.research.internetUtils.SearchNetWorkUtil
import com.lytredrock.model.research.musicdata.ArtistSong

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/26 21:09
 * version: 1.0
 */
class ArtistMusicViewModel:ViewModel() {

    private val _artistMusicData: MutableLiveData<List<ArtistSong>> = MutableLiveData()
    val artistMusicData: LiveData<List<ArtistSong>>
        get() = _artistMusicData


    fun getArtistMusic(id:String){
        SearchNetWorkUtil.receiveArtistMusic(id,_artistMusicData)
    }



}