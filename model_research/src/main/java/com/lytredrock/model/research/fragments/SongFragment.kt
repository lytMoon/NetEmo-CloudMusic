package com.lytredrock.model.research.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lytredrock.model.research.adapter.RvSongAdapter
import com.lytredrock.model.research.databinding.FragmentSongBinding
import com.lytredrock.model.research.viewModel.SongViewModel


class SongFragment : Fragment() {

    val songViewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }
    //懒加载注入viewBinding
    private val mBinding: FragmentSongBinding by lazy { FragmentSongBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onStart() {
        super.onStart()
        songViewModel.getSongInfo("海阔天空")

        val rvSongAdapter=RvSongAdapter()

        songViewModel.songData.observe(viewLifecycleOwner) { music ->
            rvSongAdapter.submitList(music)
            Log.d("96369", "(MainActivity.kt:69)-->> $music");
        }
        mBinding.rvSong.adapter=rvSongAdapter
    }

}