package com.lytredrock.emocloudmusic.frgment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.adapter.HotSingerAdapter
import com.lytredrock.emocloudmusic.databinding.FragmentConmmnityBinding
import com.lytredrock.emocloudmusic.databinding.HotSingerBinding
import com.lytredrock.emocloudmusic.viewmodel.HotSingerViewModel
import com.lytredrock.emocloudmusic.viewmodel.SongChartViewModel

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/20 19:49
 */
class HotSinger:Fragment() {
    private val myViewModel by lazy { ViewModelProvider(this)[HotSingerViewModel::class.java] }
    private var _binding: HotSingerBinding? = null
  private val binding get() = _binding!!
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      _binding = HotSingerBinding.inflate(inflater, container, false)
        myViewModel.apply {
            getHotSingerInInternet()
            hotSingerLifeData.observe(viewLifecycleOwner){
                val myAdapter=HotSingerAdapter(it,requireActivity())
                binding.rvHotSinger.adapter=myAdapter
                binding.rvHotSinger.layoutManager= GridLayoutManager(requireContext(),1)
            }
        }
        return binding.root
    }

    public override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}