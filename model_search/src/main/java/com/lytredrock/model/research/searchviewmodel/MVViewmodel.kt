package com.lytredrock.model.research.searchviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lytredrock.model.research.apisearch.MVCallBack
import com.lytredrock.model.research.musicdata.MVData
import com.lytredrock.model.research.musicdata.MVResult
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
        SearchNetWorkUtil.receiveMVInfo(keyWord, object : MVCallBack {
            override fun onRespond(t: MVData<MVResult>) {
                _mvData.postValue(t.result.mvs)
                Log.d("getMVInfo", "(MVViewmodel.kt:34)-->> ${t.result.mvs}");
            }

            override fun onFailed(e: String) {
                Log.d("getMVInfo", "(MVViewmodel.kt:35)-->> $e");
            }

        })

    }
}