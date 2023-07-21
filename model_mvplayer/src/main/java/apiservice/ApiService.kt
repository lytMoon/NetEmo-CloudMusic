package apiservice



import io.reactivex.Observable
import mvdata.Data
import mvdata.HotComment
import mvdata.MvCommentsData
import mvdata.MvInfoData
import mvdata.MvUrlData
import mvdata.UrlData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ：用于搜索功能的实现
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 20:31
 * version: 1.0
 */
interface ApiService {

    /**
     * 返回mv的信息
     */
    @GET("mv/detail")
    fun getMvInfo(@Query("mvid")mvid:String):Observable<MvInfoData<Data>>

    /**
     * 返回mv的播放链接
     */
    @GET("mv/url")
    fun getMvUrlInfo(@Query("id") id:String): Observable<MvUrlData<UrlData>>

    /**
     * 返回mv的评论
     */
    @GET("comment/mv")
    fun getComments(@Query("id")id:String,@Query("limit")limit:Int=100):Observable<MvCommentsData<HotComment>>
}