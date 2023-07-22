package viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mvdata.Data
import mvdata.HotComment
import mvdata.MvCommentsData
import mvdata.MvInfoData
import mvdata.MvUrlData
import mvdata.UrlData
import networkutils.MvNetWorkUtil

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 17:01
 * version: 1.0
 */
class MvPlayViewModel : ViewModel() {
    //储存歌曲的有关信息
    private val _mvInfoData: MutableLiveData<List<Data>> = MutableLiveData()
    val mvInfoData: LiveData<List<Data>>
        get() = _mvInfoData

    //储存歌曲的url
    private val _mvUrlData: MutableLiveData<List<UrlData>> = MutableLiveData()
    val mvUrlData: LiveData<List<UrlData>>
        get() = _mvUrlData

    //储存歌曲的评论信息
    private val _mvCommentsData: MutableLiveData<List<HotComment>> = MutableLiveData()
    val mvCommentsData: LiveData<List<HotComment>>
        get() = _mvCommentsData

    /**
     * 得到音乐的相关信息
     */
    fun getMvInfoData(key: String) {
        MvNetWorkUtil.receiveMvInfo(key, _mvInfoData)
    }

    /**
     * 得到音乐的播放链接
     */

    fun getMvUrl(key: String) {
        MvNetWorkUtil.receiveMvUrlInfo(key, _mvUrlData)

    }

    /**
     * 返回得到的评论区的数据
     */
    fun getMvComments(key: String) {
        MvNetWorkUtil.receiveMvComments(key, _mvCommentsData)

    }


}