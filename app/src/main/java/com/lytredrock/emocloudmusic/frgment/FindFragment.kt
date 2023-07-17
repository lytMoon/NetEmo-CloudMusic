package com.lytredrock.emocloudmusic.frgment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.emocloudmusic.BackFragment
import com.lytredrock.emocloudmusic.BannerAdapter
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.databinding.FragmentConmmnityBinding
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
class FindFragment: Fragment() {
     private val myViewModel by lazy { ViewModelProvider(this)[FindFragmentViewModel::class.java] }

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        var fragments= ArrayList<BackFragment>()
        for (i in 0..4){
            val itemfragment= ItemFragment()
            val bundle: Bundle=  Bundle()
            bundle.putInt("data",R.drawable.emocloud)
            itemfragment.arguments = bundle
            fragments.add(object : BackFragment {
                override fun back(): Fragment {
                    return itemfragment
                }
            })
        }
        binding.vpFind.adapter=BannerAdapter(requireActivity(),fragments)





        Timer().schedule(object : TimerTask() {
            @SuppressLint("SuspiciousIndentation")
            override fun run() {
                when(binding.vpFind.currentItem){

                    4 -> binding.vpFind.currentItem = 1

                    else->binding.vpFind.currentItem=++binding.vpFind.currentItem
                }
            }

        },2000,3000)
        return _binding?.root
    }

    public override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}