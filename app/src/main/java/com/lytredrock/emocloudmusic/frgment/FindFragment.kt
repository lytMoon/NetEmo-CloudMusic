package com.lytredrock.emocloudmusic.frgment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.emocloudmusic.BackFragment
import com.lytredrock.emocloudmusic.BannerAdapter
import com.lytredrock.emocloudmusic.MainRvAdapter
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.databinding.FragmentFindBinding
import com.lytredrock.emocloudmusic.viewmodel.FindFragmentViewModel
import java.util.Timer
import java.util.TimerTask

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 19:50
 */
class FindFragment : Fragment() {
    private val myViewModel by lazy { ViewModelProvider(this)[FindFragmentViewModel::class.java] }

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        val fragments = ArrayList<BackFragment>()




        myViewModel.apply {
            getBannerInFragment(1)
            bannerLIfeData.observe(viewLifecycleOwner) {
                for (element in it) {
                    val itemFragment = ItemFragment()
                    val bundle = Bundle()
                    bundle.putString("data",element.pic)
                    itemFragment.arguments = bundle
                    fragments.add(object : BackFragment {
                        override fun back(): Fragment {
                            return itemFragment
                        }
                    })
                }
                binding.vpFind.adapter = BannerAdapter(requireActivity(), fragments)
            }

        }



        myViewModel.apply {
            getBallInFragment()
            dataLIfeData.observe(viewLifecycleOwner) {
                binding.rvFind.adapter = MainRvAdapter(it, requireActivity())
                binding.rvFind.layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
            }
        }


        Timer().schedule(object : TimerTask() {
            @SuppressLint("SuspiciousIndentation")
            override fun run() {
                when (binding.vpFind.currentItem) {

                    fragments.size-1 -> activity?.runOnUiThread { binding.vpFind.currentItem = 0 }

                    else -> binding.vpFind.currentItem = ++binding.vpFind.currentItem
                }
            }

        }, 2000, 3000)


        return _binding?.root
    }

    public override fun onViewCreated(
        view: android.view.View, savedInstanceState: android.os.Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}