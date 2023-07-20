package apiService

import mvdata.Data
import mvdata.MvInfoData
import mvdata.MvUrlData
import mvdata.UrlData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 19:35
 * version: 1.0
 */

interface MvUrlCallBack{
    fun onRespond(t: MvUrlData<UrlData>)
    fun onFailed(e: String)
}