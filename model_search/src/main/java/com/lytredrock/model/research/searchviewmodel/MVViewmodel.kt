package com.lytredrock.model.research.searchviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.model.research.musicdata.Mv
import com.lytredrock.model.research.internetUtils.SearchNetWorkUtil

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 15:23
 * version: 1.0
 */
class MVViewmodel : ViewModel() {


    private val _mvData: MutableLiveData<List<Mv>> = MutableLiveData()
    val mvData: LiveData<List<Mv>>
        get() = _mvData


    fun getMVInfo(keyWord: String) {
        SearchNetWorkUtil.receiveMVInfo(keyWord,_mvData)

    }
}