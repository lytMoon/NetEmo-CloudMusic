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
import com.lytredrock.model.research.adapter.RvMvAdapter
import com.lytredrock.model.research.databinding.FragmentMVBinding
import com.lytredrock.model.research.viewmodel.MVViewmodel
import java.lang.NullPointerException


class MVFragment : Fragment() {
    //拿到与activity共享的viewmodel
    private val mvViewmodel by lazy {
        ViewModelProvider(requireActivity())[MVViewmodel::class.java]
    }

    //懒加载注入viewBinding
    private val mBinding: FragmentMVBinding by lazy {
        FragmentMVBinding.inflate(
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
        mvViewmodel.mvData.observe(viewLifecycleOwner) {
            try {
                mBinding.rvMv.adapter = RvMvAdapter(it, requireActivity())
                mBinding.rvMv.layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
            } catch (e: NullPointerException) {
                Log.d("NullPointerException", "(MVFragment.kt:52)-->> ");
            }

        }


    }


}