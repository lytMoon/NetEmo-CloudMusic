//package mvdata
//
///**
// * description ：
// * author : lytMoon
// * email : yytds@foxmail.com
// * date : 2023/7/21 20:23
// * version: 1.0
// */
//data class MvCommentsData(
//    val cnum: Int,
//    val code: Int,
//    val commentBanner: Any,
//    val hotComments: List<HotComment>,
//    val isMusician: Boolean,
//    val more: Boolean,
//    val moreHot: Boolean,
//    val topComments: List<Any>,
//    val total: Int,
//    val userId: Long
//)
//
//
//data class HotComment(
//    val beReplied: List<Any>,
//    val commentId: Int,
//    val commentLocationType: Int,
//    val content: String,
//    val contentResource: Any,
//    val decoration: Any,
//    val expressionUrl: Any,
//    val grade: Any,
//    val ipLocation: IpLocationXX,
//    val liked: Boolean,
//    val likedCount: Int,
//    val needDisplayTime: Boolean,
//    val owner: Boolean,
//    val parentCommentId: Int,
//    val pendantData: PendantDataX,
//    val repliedMark: Any,
//    val richContent: Any,
//    val showFloorComment: Any,
//    val status: Int,
//    val time: Long,
//    val timeStr: String,
//    val user: UserXX,
//    val userBizLevels: Any
//)
//
//data class BeReplied(
//    val beRepliedCommentId: Long,
//    val content: String,
//    val expressionUrl: Any,
//    val ipLocation: IpLocationX,
//    val richContent: String,
//    val status: Int,
//    val user: User
//)
//
//class Decoration
//
//data class IpLocationX(
//    val ip: Any,
//    val location: String,
//    val userId: Long
//)
//
//data class PendantData(
//    val id: Int,
//    val imageUrl: String
//)
//
//data class UserX(
//    val anonym: Int,
//    val authStatus: Int,
//    val avatarDetail: AvatarDetail,
//    val avatarUrl: String,
//    val commonIdentity: Any,
//    val expertTags: Any,
//    val experts: Any,
//    val followed: Boolean,
//    val liveInfo: Any,
//    val locationInfo: Any,
//    val mutual: Boolean,
//    val nickname: String,
//    val remarkName: Any,
//    val socialUserId: Any,
//    val target: Any,
//    val userId: Long,
//    val userType: Int,
//    val vipRights: VipRights,
//    val vipType: Int
//)
//
//data class User(
//    val avatarUrl: String,
//)
//
//data class Experts(
//    val `1`: String,
//    val `2`: String
//)
//
//data class AvatarDetail(
//    val identityIconUrl: String,
//    val identityLevel: Int,
//    val userType: Int
//)
//
//data class VipRights(
//    val associator: Associator,
//    val musicPackage: MusicPackage,
//    val redVipAnnualCount: Int,
//    val redVipLevel: Int,
//    val redplus: Any
//)
//
//data class Associator(
//    val iconUrl: String,
//    val rights: Boolean,
//    val vipCode: Int
//)
//
//
//data class IpLocationXX(
//    val ip: Any,
//    val location: String,
//    val userId: Any
//)
//
//data class UserXX(
//    val anonym: Int,
//    val authStatus: Int,
//    val avatarDetail: AvatarDetailX,
//    val avatarUrl: String,
//    val commonIdentity: Any,
//    val expertTags: List<String>,
//    val experts: ExpertsX,
//    val followed: Boolean,
//    val liveInfo: Any,
//    val locationInfo: Any,
//    val mutual: Boolean,
//    val nickname: String,
//    val remarkName: Any,
//    val socialUserId: Any,
//    val target: Any,
//    val userId: Int,
//    val userType: Int,
//    val vipType: Int
//)
//
