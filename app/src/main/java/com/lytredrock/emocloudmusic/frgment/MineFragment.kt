package com.lytredrock.emocloudmusic.frgment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.lytredrock.emocloudmusic.Collect
import com.lytredrock.emocloudmusic.Download
import com.lytredrock.emocloudmusic.databinding.FragmentMineBinding
import com.lytredrock.emocloudmusic.viewmodel.MineFragmentViewModel

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 17:47
 */
class MineFragment : Fragment() {
    private val myViewModel by lazy { ViewModelProvider(this)[MineFragmentViewModel::class.java] }
    private var _binding: FragmentMineBinding? = null
    val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            binding.tvMineName.text = "游客"
        }
    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        binding.ivHead.bringToFront()
        binding.ivCollect.setOnClickListener {
            val intent = Intent(requireContext(), Collect::class.java)
            startActivity(intent)
        }
        binding.ivDownloadRecord.setOnClickListener {
            val intent = Intent(requireContext(), Download::class.java)
            startActivity(intent)
        }

        binding.tvMineName.setOnClickListener {
            ARouter.getInstance()
                .build("/login/start")
                .navigation()
            handler.postDelayed(runnable, 1000)

        }

        return _binding?.root

    }

    public override fun onViewCreated(
        view: android.view.View,
        savedInstanceState: android.os.Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacks(runnable)
    }

}