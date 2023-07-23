package com.lytredrock.model.player.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lytredrock.model.player.databinding.FragmentPageOneBinding
import com.lytredrock.model.player.viewmodel.MusicPlayerViewModel

class PageOneFragment : Fragment() {


    //懒加载注入viewModel
    private val playerViewModel by lazy {
        ViewModelProvider(requireActivity())[MusicPlayerViewModel::class.java]
    }
    //懒加载注入viewBinding
    private val mBinding: FragmentPageOneBinding by lazy {
        FragmentPageOneBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerViewModel.musicInfo.observe(viewLifecycleOwner){
            Glide.with(this)
                .load(it[0].al.picUrl)
                .transform(RoundedCorners(360))
                .into(mBinding.ivMusicCover)
        }
        /**
         * 下面几个参数分别表示：从多少度旋转，旋转到多少度，x轴旋转中心，y轴旋转中心，持续时间（旋转周期），无限次数
         */
        val animation = RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 10000
        animation.repeatCount = Animation.INFINITE
        animation.interpolator = LinearInterpolator() // 使用 LinearInterpolator 插值器，保持播放速度一样
        mBinding.ivMusicCover.startAnimation(animation)

    }


}