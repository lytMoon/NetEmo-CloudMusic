package mvdata

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/27 15:07
 * version: 1.0
 */
data class MvCommentsData<T>(
    val hotComments: List<HotComment>,
)

data class HotComment(
    val content: String,
    val user: UserXX,
)


data class UserXX(
    val avatarUrl: String,

    val nickname: String,

    )

