package com.lytredrock.emocloudmusic

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * description ： vp2的适配器
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 17:32
 */
class FragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: ArrayList<BackFragment>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].back()
    }

}