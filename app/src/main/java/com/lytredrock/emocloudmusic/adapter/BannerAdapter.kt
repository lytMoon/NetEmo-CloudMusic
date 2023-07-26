package com.lytredrock.emocloudmusic.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lytredrock.emocloudmusic.frgment.BackFragment

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/17 11:38
 */
class BannerAdapter(
    fragmentActivity: FragmentActivity, private val fragments: ArrayList<BackFragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
            return fragments[position].back()
    }

}