package mvdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 17:26
 * version: 1.0
 */
data class MvUrlData<T>(
    val code: Int,
    val `data`: UrlData
)

data class UrlData(
    val code: Int,
    val expi: Int,
    val fee: Int,
    val id: Int,
    val md5: String,
    val msg: String,
    val mvFee: Int,
    val promotionVo: Any,
    val r: Int,
    val size: Int,
    val st: Int,
    val url: String
)