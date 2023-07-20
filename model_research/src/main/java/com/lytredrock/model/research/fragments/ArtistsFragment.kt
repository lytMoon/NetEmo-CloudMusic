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
import com.lytredrock.model.research.R
import com.lytredrock.model.research.adapter.RvArtistAdapter
import com.lytredrock.model.research.adapter.RvSongAdapter
import com.lytredrock.model.research.databinding.FragmentArtistsBinding
import com.lytredrock.model.research.databinding.FragmentSongBinding
import com.lytredrock.model.research.viewModel.ArtistsViewModel
import com.lytredrock.model.research.viewModel.SongViewModel


class ArtistsFragment : Fragment() {
    //拿到与activity共享的viewmodel
    private val artistViewModel by lazy {
        ViewModelProvider(requireActivity())[ArtistsViewModel::class.java]
    }

    //懒加载注入viewBinding
    private val mBinding: FragmentArtistsBinding by lazy {
        FragmentArtistsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }


    override fun onResume() {
        super.onResume()
        artistViewModel.artistData.observe(viewLifecycleOwner) {

            mBinding.rvArtist.adapter = RvArtistAdapter(it, requireActivity())
            mBinding.rvArtist.layoutManager =
                GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
            Log.d("96369", "(MainActivity.kt:69)-->> $it")
        }


    }
}
