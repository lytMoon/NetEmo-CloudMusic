package com.lytredrock.emocloudmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.emocloudmusic.data.Collect

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/24 19:51
 */
class MineFragmentViewModel:ViewModel() {
    val collectLifeData by lazy {
        MutableLiveData<List<Collect>>()
    }

}