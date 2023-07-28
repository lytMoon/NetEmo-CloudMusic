package com.lytredrock.emocloudmusic.frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lytredrock.emocloudmusic.adapter.FragmentAdapter
import com.lytredrock.emocloudmusic.databinding.FragmentConmmnityBinding

/**
 * description ： 热门页面的fragment
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 19:52
 */
class HotFragment : Fragment() {


    private var _binding: FragmentConmmnityBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConmmnityBinding.inflate(inflater, container, false)


        var fragments = ArrayList<BackFragment>()
        fragments.add(object : BackFragment {
            override fun back(): Fragment {
                return SongChartFragment()
            }
        })
        fragments.add(object : BackFragment {
            override fun back(): Fragment {
                return HotSinger()
            }
        })
        fragments.add(object : BackFragment {
            override fun back(): Fragment {
                return HotSongListFragment()
            }
        })
        binding.vp2Hot.adapter = FragmentAdapter(requireActivity(), fragments)
//vp2与tablayout的联动
        TabLayoutMediator(binding.tabLayout2, binding.vp2Hot) { tab, position ->
            when (position) {
                0 -> tab.text = "歌榜"
                1 -> tab.text = "热门歌手"
                else -> tab.text = "热门歌单"
            }
        }.attach()




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
    }
}