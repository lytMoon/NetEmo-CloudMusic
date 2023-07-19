package com.lytredrock.model.player.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.model.player.apiservice.UrlCallBack
import com.lytredrock.model.player.networkutils.PlayerNetWorkUtils
import com.lytredrock.model.player.playerData.Data
import com.lytredrock.model.player.playerData.UrlData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 16:26
 * version: 1.0
 */
class PlayerViewModel:ViewModel() {

    private val _musicInfo: MutableLiveData<List<Data>> = MutableLiveData()
    val musicInfo: LiveData<List<Data>>
        get() =_musicInfo

    /**
     * 获取音乐的播放链接
     */
    fun getMusicUrl(key:String){

        PlayerNetWorkUtils.getUrl(key,object:UrlCallBack{
            override fun onRespond(t: UrlData<Data>) {
                _musicInfo.postValue(t.data)
                Log.d("getMusicUrl","(PlayerViewModel.kt:24)-->> ${t.data}");
            }
            override fun onFailed(e: String) {
               Log.d("getMusicUrl","(PlayerViewModel.kt:33)-->> $e");
            }

        })

    }
}