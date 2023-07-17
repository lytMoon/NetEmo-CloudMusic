package com.lytredrock.model.research.musicdata

/**
 * description ：接受搜索得到的音乐
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/17 20:35
 * version: 1.0
 */
data class MusicSearchData(
    val code: Int,
    val result: Result
)

data class Result(
    val artist: List<Artist>,
    val new_mlog: List<NewMlog>,
    val orders: List<String>
)

data class Artist(
    val albumSize: Int,
    val alg: String,
    val alias: List<Any>,
    val briefDesc: String,
    val fansSize: Int,
    val id: Int,
    val img1v1Id: Long,
    val img1v1Id_str: String,
    val img1v1Url: String,
    val musicSize: Int,
    val mvSize: Int,
    val name: String,
    val occupation: String,
    val officialTags: List<Any>,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val searchCircle: Any,
    val trans: String,
    val transNames: List<String>,
    val videoSize: Int
)

data class NewMlog(
    val alg: String,
    val baseInfo: BaseInfo,
    val internalType: Any,
    val resourceId: String,
    val resourceName: String,
    val resourceType: String
)

data class BaseInfo(
    val alg: Any,
    val id: String,
    val matchField: Int,
    val matchFieldContent: Any,
    val mlogBaseDataType: Int,
    val position: Any,
    val reason: Any,
    val resource: Resource,
    val sameCity: Boolean,
    val type: Int
)

data class Resource(
    val mlogBaseData: MlogBaseData,
    val mlogExtVO: MlogExtVO,
    val shareUrl: String,
    val status: Int,
    val userProfile: UserProfile
)

data class MlogBaseData(
    val audio: Any,
    val coverColor: Int,
    val coverDynamicUrl: Any,
    val coverHeight: Int,
    val coverPicKey: String,
    val coverUrl: String,
    val coverWidth: Int,
    val duration: Int,
    val id: String,
    val interveneText: String,
    val pubTime: Long,
    val text: String,
    val threadId: String,
    val type: Int
)

data class MlogExtVO(
    val artistName: Any,
    val artists: List<Any>,
    val canCollect: Boolean,
    val channelTag: Any,
    val commentCount: Int,
    val likedCount: Int,
    val playCount: Int,
    val rcmdInfo: Any,
    val song: Song,
    val specialTag: Any,
    val strongPushIcon: Any,
    val strongPushMark: Any
)

data class UserProfile(
    val avatarUrl: String,
    val followed: Boolean,
    val isAnchor: Boolean,
    val nickname: String,
    val userId: Long,
    val userType: Int
)

data class Song(
    val albumName: String,
    val artists: List<ArtistX>,
    val coverUrl: String,
    val duration: Int,
    val endTime: Any,
    val id: Int,
    val name: String,
    val privilege: Any,
    val startTime: Any
)

data class ArtistX(
    val artistId: Int,
    val artistName: String
)