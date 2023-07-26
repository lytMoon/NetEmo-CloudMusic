package com.lytredrock.emocloudmusic.frgment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import com.lytredrock.emocloudmusic.adapter.SingerSongAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.emocloudmusic.Collect
import com.lytredrock.emocloudmusic.Download
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.databinding.FragmentFindBinding
import com.lytredrock.emocloudmusic.databinding.FragmentMineBinding
import com.lytredrock.emocloudmusic.viewmodel.MineFragmentViewModel

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 17:47
 */
class MineFragment:Fragment() {
    private val myViewModel by lazy { ViewModelProvider(this)[MineFragmentViewModel::class.java] }

    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        binding.ivHead.bringToFront()
        binding.ivCollect.setOnClickListener {
            val intent=Intent(requireContext(),Collect::class.java)
            startActivity(intent)
        }
        binding.ivDownloadRecord.setOnClickListener {
            val intent=Intent(requireContext(),Download::class.java)
            startActivity(intent)
        }

        return _binding?.root

    }

    public override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}