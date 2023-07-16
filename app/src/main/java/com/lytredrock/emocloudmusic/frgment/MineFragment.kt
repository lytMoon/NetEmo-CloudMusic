package com.lytredrock.emocloudmusic.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.databinding.FragmentFindBinding
import com.lytredrock.emocloudmusic.databinding.FragmentMineBinding

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 17:47
 */
class MineFragment:Fragment() {

    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
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