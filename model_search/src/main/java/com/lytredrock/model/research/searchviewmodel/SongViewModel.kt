package com.lytredrock.model.research.searchviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.model.research.apisearch.SongCallBack
import com.lytredrock.model.research.musicdata.Result
import com.lytredrock.model.research.musicdata.SearchSongData
import com.lytredrock.model.research.musicdata.Song
import com.lytredrock.model.research.internetUtils.SearchNetWorkUtil

/**
 * description ：服务于SongFragment的viewModel
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/18 16:03
 * version: 1.0
 */
class SongViewModel : ViewModel() {

    private val _songData: MutableLiveData<List<Song>> = MutableLiveData()
    val songData: LiveData<List<Song>>
        get() = _songData


    //得到单曲的信息
    fun getSongInfo(keyword: String) {
        SearchNetWorkUtil.receiveSongInfo(keyword, object : SongCallBack {
            override fun onRespond(t: SearchSongData<Result>) {
                _songData.postValue(t.result.songs)
                Log.d("getSongInfo", "(SongViewModel.kt:30)-->> ${t.result.songs}");
            }

            override fun onFailed(e: String) {
                Log.d("getSongInfo", "(SongViewModel.kt:36)-->> $e");
            }
        })
    }


}