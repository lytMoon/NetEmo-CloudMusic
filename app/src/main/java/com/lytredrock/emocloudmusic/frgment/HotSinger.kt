package com.lytredrock.emocloudmusic.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.databinding.FragmentConmmnityBinding
import com.lytredrock.emocloudmusic.databinding.HotSingerBinding

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/20 19:49
 */
class HotSinger:Fragment() {
    private var _binding: HotSingerBinding? = null
  private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      _binding = HotSingerBinding.inflate(inflater, container, false)
        return binding.root
    }

    public override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}