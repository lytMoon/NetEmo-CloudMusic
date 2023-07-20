package mvdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 17:13
 * version: 1.0
 */
data class MvInfoData<T>(
    val code: Int,
    val `data`: Data,
    val mp: Mp,
    val subed: Boolean
)

data class Data(
    val artistId: Int,
    val artistName: String,
    val artists: List<Artist>,
    val briefDesc: String,
    val brs: List<Br>,
    val commentCount: Int,
    val commentThreadId: String,
    val cover: String,
    val coverId: Long,
    val coverId_str: String,
    val desc: String,
    val duration: Int,
    val id: Int,
    val nType: Int,
    val name: String,
    val playCount: Int,
    val price: Any,
    val publishTime: String,
    val shareCount: Int,
    val subCount: Int,
    val videoGroup: List<VideoGroup>
)

data class Mp(
    val cp: Int,
    val dl: Int,
    val fee: Int,
    val id: Int,
    val msg: Any,
    val mvFee: Int,
    val normal: Boolean,
    val payed: Int,
    val pl: Int,
    val sid: Int,
    val st: Int,
    val unauthorized: Boolean
)

data class Artist(
    val followed: Boolean,
    val id: Int,
    val img1v1Url: String,
    val name: String
)

data class Br(
    val br: Int,
    val point: Int,
    val size: Int
)

data class VideoGroup(
    val id: Int,
    val name: String,
    val type: Int
)