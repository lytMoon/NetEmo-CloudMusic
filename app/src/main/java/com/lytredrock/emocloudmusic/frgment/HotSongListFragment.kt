package com.lytredrock.emocloudmusic.frgment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lytredrock.emocloudmusic.SongListActivity
import com.lytredrock.emocloudmusic.adapter.HotSongListAdapter
import com.lytredrock.emocloudmusic.databinding.HotSonglistBinding
import com.lytredrock.emocloudmusic.viewmodel.HotSongListViewModel

/**
 * description ： 热门歌单的fragment
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/20 19:48
 */
class HotSongListFragment : Fragment() {

    private val myViewModel by lazy { ViewModelProvider(this)[HotSongListViewModel::class.java] }
    private var _binding: HotSonglistBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HotSonglistBinding.inflate(inflater, container, false)
        myViewModel.apply {
            getHotSongListInInternet()
            hotSongListLifeData.observe(viewLifecycleOwner) {
                val myAdapter = HotSongListAdapter(it, requireActivity())
                binding.rvHotSongList.adapter = myAdapter
                binding.rvHotSongList.layoutManager = GridLayoutManager(requireContext(), 3)
                myAdapter.setOnclick(object : HotSongListAdapter.ClickInterface {
                    override fun onImageviewClick(view: View, position: Int) {
                        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            view,
                            "ShareElement1"
                        ).toBundle()
                        val intent = Intent(requireContext(), SongListActivity::class.java)
                        intent.putExtra("id", it[position].id)
                        intent.putExtra("name", it[position].name)
                        intent.putExtra("photo", it[position].coverImgUrl)
                        startActivity(intent, bundle)
                    }

                })
            }
        }

        return binding.root
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