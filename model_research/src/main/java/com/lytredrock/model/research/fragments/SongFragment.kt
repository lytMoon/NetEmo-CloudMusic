package com.lytredrock.model.research.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.model.research.adapter.RvSongAdapter
import com.lytredrock.model.research.databinding.FragmentSongBinding
import com.lytredrock.model.research.databinding.ResearchMainBinding
import com.lytredrock.model.research.viewModel.SongViewModel


class SongFragment : Fragment() {
    private val binding: ResearchMainBinding by lazy { ResearchMainBinding.inflate(layoutInflater) }
    private val songViewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }


    //懒加载注入viewBinding
    private val mBinding: FragmentSongBinding by lazy { FragmentSongBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songViewModel.getSongInfo("心跳的证明")
        songViewModel.songData.observe(viewLifecycleOwner) {
            mBinding.rvSong.adapter = RvSongAdapter(it, requireActivity())
            mBinding.rvSong.layoutManager =
                GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
            Log.d("96369", "(MainActivity.kt:69)-->> $it")

        }



    }

    override fun onResume() {
        super.onResume()

    }
}



