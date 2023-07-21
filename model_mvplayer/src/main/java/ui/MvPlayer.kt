package ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lytredrock.lib.base.BaseUtils.myToast
import com.lytredrock.lib.base.BaseUtils.transparentStatusBar
import com.lytredrock.model.mvplayer.R
import com.lytredrock.model.mvplayer.databinding.ActivityMvPlayerBinding
import com.lytredrock.model.mvplayer.databinding.BottomSheetLayoutBinding
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import viewmodel.MvPlayViewModel
import kotlin.properties.Delegates

@Route(path = "/mv/mvPlay")
class MvPlayer : AppCompatActivity() {

    @Autowired
    lateinit var mvUid: String

    @Autowired
    lateinit var mvName: String
    private lateinit var orientationUtils: OrientationUtils
    private var mPosition by Delegates.notNull<Long>()

    //懒加载注入viewmodel
    private val myViewModel by lazy {
        ViewModelProvider(this)[MvPlayViewModel::class.java]
    }

    //懒加载注入viewBinding
    private val mBinding: ActivityMvPlayerBinding by lazy {
        ActivityMvPlayerBinding.inflate(
            layoutInflater
        )
    }
    //懒加载注入viewBinding
    private val rvBinding: BottomSheetLayoutBinding by lazy {
        BottomSheetLayoutBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        ARouter.getInstance().inject(this@MvPlayer)
        iniPlayer()
        iniBar()
        iniClick()
        iniUpInfo(mvUid)
        iniPlayMv(mvUid, mvName)
    }


    /**
     * 在这里实现播放mv的逻辑
     */
    private fun iniPlayMv(uid: String, mvName: String) {
        myViewModel.getMvUrl(uid)
        myViewModel.mvUrlData.observe(this) {

            mBinding.iVideoPlayer.setUp(it[0].url, false, mvName)
            mBinding.iVideoPlayer.startPlayLogic()
        }
    }


    /**
     * 更新我们播放器ui的有关信息
     */
    private fun iniUpInfo(key: String) {
        Log.d("iniUpInfo", "(MvPlayer.kt:69)-->> $key");
        myViewModel.getMvInfoData(key)
        myViewModel.mvInfoData.observe(this) {
            Glide.with(this)
                .load(it[0].artists[0].img1v1Url)
                .transform(RoundedCorners(20))
                .into(mBinding.authorImg)

            mBinding.authorName.text = it[0].artistName
            mBinding.authorSong.text = it[0].name
            mBinding.videoSharesNum.text = formatNumber(it[0].shareCount)
            mBinding.videoCommentNum.text = formatNumber(it[0].commentCount)
            mBinding.videoLikesNum.text = formatNumber(it[0].subCount)

        }

    }

    /**
     * 处理数字的单位
     */
    fun formatNumber(num: Int): String {
        return when {
            num in 1000..999999 -> "${num / 1000}k"
            num >= 1000000 -> "${num / 1000000}M"
            else -> num.toString()
        }
    }

    private fun iniPlayer() {
        //获取屏幕的旋转状态
        orientationUtils = OrientationUtils(this, mBinding.iVideoPlayer)
        GSYVideoOptionBuilder().setUrl(null)
            .setVideoTitle(null) //movie title
            .setIsTouchWiget(false) //小屏时不触摸滑动
            .setRotateViewAuto(false) //是否开启自动旋转
            .setLockLand(false) //一全屏就锁屏横屏，默认false竖屏，可配合setRotateViewAuto使用
            .setAutoFullWithSize(true) //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
            .setShowFullAnimation(true) //全屏动画
            .setNeedLockFull(false) //是否需要全屏锁定屏幕功能
            .setCacheWithPlay(true) //是否边缓存，m3u8等无效
            .setReleaseWhenLossAudio(false) //音频焦点冲突时是否释放
            .setSeekRatio(8f)
            .setSeekOnStart(0)
            .setIsTouchWigetFull(true) //是否可以全屏滑动界面改变进度，声音等
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    orientationUtils.isEnable = false
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils.backToProtVideo()
                }
            })


    }


    /**
     * 开启沉浸式状态栏
     */
    private fun iniBar() {
        transparentStatusBar(window, false)
    }

    /**
     * 下面是配置播放器的生命周期
     */
    override fun onPause() {
        super.onPause()
        mBinding.iVideoPlayer.currentPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.iVideoPlayer.currentPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.iVideoPlayer.currentPlayer.release()
    }


    /**
     * 下面处理我们的点击事件
     */
    @SuppressLint("InflateParams")
    private fun iniClick() {
        /**
         * 分享的点击事件
         */
        mBinding.videoShare.setOnClickListener {
            myToast("分享", this)
            //下面是采用系统的分享工具
            myViewModel.mvUrlData.observe(this) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"//指定类型是纯文本分享
                intent.putExtra(Intent.EXTRA_SUBJECT, "音乐MV链接")
                intent.putExtra(Intent.EXTRA_TEXT, it[0].url)
                startActivity(Intent.createChooser(intent, "分享到"))
            }

        }

        /**
         * 旋转屏幕的点击事件
         */
        mBinding.fullScreen.setOnClickListener {
            mPosition = mBinding.iVideoPlayer.currentPositionWhenPlaying
            Log.d("fullScreen", "(MvPlayer.kt:187)-->> $mPosition");
            orientationUtils.resolveByClick() //LANDSCAPE
            mBinding.iVideoPlayer.startWindowFullscreen(this, false, true)
            mBinding.iVideoPlayer.seekTo(mPosition)

        }
        /**
         * 点赞点击事件（还没有完成）
         */
        mBinding.videoLikes.setOnClickListener {
            myToast("功能还在完善中", this)
        }


        /**
         * 评论区点击事件的实现
         */
        mBinding.videoComment.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
            bottomSheetDialog.setContentView(bottomSheetView)
            // 设置 BottomSheetDialog 的一些参数
            bottomSheetDialog.setCancelable(true)
            bottomSheetDialog.setCanceledOnTouchOutside(true)
            bottomSheetDialog.behavior.isDraggable = true
            bottomSheetDialog.show()


        }



    }


}


