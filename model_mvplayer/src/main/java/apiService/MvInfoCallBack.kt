package apiService

import mvdata.Data
import mvdata.MvInfoData

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 17:47
 * version: 1.0
 */
interface MvInfoCallBack {
    fun onRespond(t: MvInfoData<Data>)
    fun onFailed(e: String)
}