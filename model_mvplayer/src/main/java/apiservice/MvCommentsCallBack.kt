package apiservice

import mvdata.Data
import mvdata.HotComment
import mvdata.MvCommentsData
import mvdata.MvInfoData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/21 20:46
 * version: 1.0
 */
interface MvCommentsCallBack {
    fun onRespond(t: MvCommentsData<HotComment>)
    fun onFailed(e: String)
}