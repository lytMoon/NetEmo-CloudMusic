package com.lytredrock.model.research.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.model.research.apisearch.ArtistCallBack
import com.lytredrock.model.research.musicdata.Artist
import com.lytredrock.model.research.musicdata.ArtistResult
import com.lytredrock.model.research.musicdata.SearchArtistData
import com.lytredrock.model.research.internetUtils.SearchNetWorkUtil

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 11:46
 * version: 1.0
 */
class ArtistsViewModel : ViewModel() {
    private val _artistData: MutableLiveData<List<Artist>> = MutableLiveData()
    val artistData: LiveData<List<Artist>>
        get() = _artistData

    fun getArtistsInfo(keyword: String) {
        SearchNetWorkUtil.receiveArtistsInfo(keyword, object : ArtistCallBack {
            override fun onRespond(t: SearchArtistData<ArtistResult>) {

                _artistData.postValue(t.result.artists)
//                Log.d("ArtistsViewModel","(ArtistsViewModel.kt:30)-->> ${t.result.artists[0]}")
            }

            override fun onFailed(e: String) {
                Log.d("ArtistsViewModel", "(ArtistsViewModel.kt:30)-->> $e");
            }

        })


    }
}