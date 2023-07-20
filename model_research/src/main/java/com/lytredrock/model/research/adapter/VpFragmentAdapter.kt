package com.lytredrock.model.research.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lytredrock.model.research.apiService.BackInterface

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/18 11:29
 * version: 1.0
 */
class VpFragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: ArrayList<BackInterface>
) : FragmentStateAdapter(fragmentActivity) {
    //返回fragment的总个数


    override fun getItemCount(): Int {
        return fragments.size
    }

    //
    override fun createFragment(position: Int): Fragment {
        return fragments.get(position).back()
        //下面其实是另一种写法，直接制指定每次返回的是一个新的类，而不是集合中的引用
//        return when(position){
//            0-> SongFragment()
//            else-> ArtistsFragment()
//        }

    }

}