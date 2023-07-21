package com.lytredrock.model.research.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.model.research.adapter.RvSongAdapter
import com.lytredrock.model.research.databinding.FragmentSongBinding
import com.lytredrock.model.research.viewmodel.SongViewModel
import java.lang.NullPointerException


class SongFragment : Fragment() {
    private val songViewModel by lazy {
        ViewModelProvider(requireActivity())[SongViewModel::class.java]
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
        Log.d("lifeCycle", "(SongFragment.kt:40)-->>  onViewCreated");
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "(SongFragment.kt:57)-->> onResume");
        songViewModel.songData.observe(viewLifecycleOwner) {
            try {
                mBinding.rvSong.adapter = RvSongAdapter(it, requireActivity())
                mBinding.rvSong.layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
            } catch (e: NullPointerException) {
                Log.d("NullPointerException", "(SongFragment.kt:50)-->> ");
            }

        }

    }

}



