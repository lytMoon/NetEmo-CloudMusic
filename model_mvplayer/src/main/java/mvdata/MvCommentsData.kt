package mvdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/21 20:23
 * version: 1.0
 */
data class MvCommentsData<T>(
    val cnum: Int,
    val code: Int,
    val hotComments: List<HotComment>,
    val total: Int,
    val userId: Long
)


data class HotComment(
    val content: String,
    val user: UserXX,
)
data class UserXX(
    val avatarUrl: String,
    val nickname: String,
)

