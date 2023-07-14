package com.lytredrock.lib.network.musicData

/**
 * description ：用于接受音乐信息的数据类
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/14 19:13
 * version: 1.0
 */
data class MusicComment<T>(
    val cnum: Int,
    val code: Int,
    val commentBanner: Any,
    val comments: List<Comment>,
    val hotComments: List<HotComment>,
    val isMusician: Boolean,
    val more: Boolean,
    val moreHot: Boolean,
    val topComments: List<Any>,
    val total: Int,
    val userId: Long
)

data class Comment(
    val beReplied: List<BeReplied>,
    val commentId: Long,
    val commentLocationType: Int,
    val content: String,
    val contentResource: Any,
    val decoration: Decoration,
    val expressionUrl: Any,
    val grade: Any,
    val ipLocation: IpLocationX,
    val liked: Boolean,
    val likedCount: Int,
    val needDisplayTime: Boolean,
    val owner: Boolean,
    val parentCommentId: Long,
    val pendantData: PendantData,
    val repliedMark: Any,
    val richContent: Any,
    val showFloorComment: Any,
    val status: Int,
    val time: Long,
    val timeStr: String,
    val user: UserX,
    val userBizLevels: Any
)

data class HotComment(
    val beReplied: List<BeRepliedX>,
    val commentId: Long,
    val commentLocationType: Int,
    val content: String,
    val contentResource: Any,
    //val decoration: DecorationX,
    val expressionUrl: Any,
    val grade: Any,
    val ipLocation: IpLocationX,
    val liked: Boolean,
    val likedCount: Int,
    val needDisplayTime: Boolean,
    val owner: Boolean,
    val parentCommentId: Int,
  //  val pendantData: PendantDataX,
    val repliedMark: Any,
    val richContent: Any,
    val showFloorComment: Any,
    val status: Int,
    val time: Long,
    val timeStr: String,
    val user: UserXXX,
    val userBizLevels: Any
)

data class BeReplied(
    val beRepliedCommentId: Long,
    val content: String,
    val expressionUrl: Any,
    val ipLocation: IpLocationX,
    val richContent: Any,
    val status: Int,
    val user: User
)

class Decoration

data class IpLocationX(
    val ip: Any,
    val location: String,
    val userId: Any
)

data class PendantData(
    val id: Int,
    val imageUrl: String
)

data class UserX(
    val anonym: Int,
    val authStatus: Int,
    val avatarDetail: Any,
    val avatarUrl: String,
    val commonIdentity: Any,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val liveInfo: Any,
    val locationInfo: Any,
    val mutual: Boolean,
    val nickname: String,
    val remarkName: Any,
    val socialUserId: Any,
    val target: Any,
    val userId: Int,
    val userType: Int,
    val vipRights: VipRights,
    val vipType: Int
)

data class User(
    val anonym: Int,
    val authStatus: Int,
    val avatarDetail: Any,
    val avatarUrl: String,
    val commonIdentity: Any,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val liveInfo: Any,
    val locationInfo: Any,
    val mutual: Boolean,
    val nickname: String,
    val remarkName: Any,
    val socialUserId: Any,
    val target: Any,
    val userId: Int,
    val userType: Int,
    val vipRights: Any,
    val vipType: Int
)

data class VipRights(
    val associator: Associator,
    val musicPackage: MusicPackage,
    val redVipAnnualCount: Int,
    val redVipLevel: Int,
    val redplus: Any
)

data class Associator(
    val iconUrl: String,
    val rights: Boolean,
    val vipCode: Int
)

data class MusicPackage(
    val iconUrl: String,
    val rights: Boolean,
    val vipCode: Int
)

data class BeRepliedX(
    val beRepliedCommentId: Int,
    val content: String,
    val expressionUrl: Any,
    val ipLocation: IpLocationX,
    val richContent: Any,
    val status: Int,
    val user: UserXX
)

data class UserXXX(
    val anonym: Int,
    val authStatus: Int,
   // val avatarDetail: AvatarDetailX,
    val avatarUrl: String,
    val commonIdentity: Any,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val liveInfo: Any,
    val locationInfo: Any,
    val mutual: Boolean,
    val nickname: String,
    val remarkName: Any,
    val socialUserId: Any,
    val target: Any,
    val userId: Int,
    val userType: Int,
    val vipRights: VipRightsX,
    val vipType: Int
)

data class UserXX(
    val anonym: Int,
    val authStatus: Int,
    val avatarDetail: AvatarDetail,
    val avatarUrl: String,
    val commonIdentity: Any,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val liveInfo: Any,
    val locationInfo: Any,
    val mutual: Boolean,
    val nickname: String,
    val remarkName: Any,
    val socialUserId: Any,
    val target: Any,
    val userId: Int,
    val userType: Int,
    val vipRights: Any,
    val vipType: Int
)

data class AvatarDetail(
    val identityIconUrl: String,
    val identityLevel: Int,
    val userType: Int
)

data class VipRightsX(
 //   val associator: AssociatorX,
  //  val musicPackage: MusicPackageX,
    val redVipAnnualCount: Int,
    val redVipLevel: Int,
    val redplus: Any
)