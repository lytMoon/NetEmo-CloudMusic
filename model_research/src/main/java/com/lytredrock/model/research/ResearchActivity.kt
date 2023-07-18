package com.lytredrock.model.research

import BaseActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.lytredrock.model.research.adapter.VpFragmentAdapter
import com.lytredrock.model.research.apiService.SongCallBack
import com.lytredrock.model.research.databinding.ResearchMainBinding
import com.lytredrock.model.research.fragments.ArtistsFragment
import com.lytredrock.model.research.fragments.SongFragment
import com.lytredrock.model.research.networkUtils.SearchNetWorkUtil
import com.lytredrock.model.research.viewModel.SongViewModel


class ResearchActivity : BaseActivity() {


    private val titlesList = arrayListOf<String>()
    private val fragmentList = arrayListOf<BackInterface>()
    private val songFragment= SongFragment()

    //懒加载注入viewBinding
    private val mBinding: ResearchMainBinding by lazy { ResearchMainBinding.inflate(layoutInflater) }
    //懒加载注入viewmodel
    val researchViewModel by lazy {
        ViewModelProvider(this)[ResearchViewModel::class.java]
    }
    private val songViewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        iniActionBar()
        iniTabLayout()
        iniTest()
        iniClick()
    }

    private fun iniClick() {
        mBinding.tvSearch.setOnClickListener{
            val key = mBinding.searchView.query.toString()
            if (TextUtils.isEmpty(key)){
                myToast("输入不能为空",this)

            }
            else{




            }
            Log.d("tvSearch","(ResearchActivity.kt:41)-->> $key");
        }
    }

    private fun iniTest() {

    }

    private fun iniTabLayout() {

        fragmentList.let {
            it.add( object :BackInterface{
                override fun back(): Fragment {
                    return   SongFragment()
                }
            })
            it.add(object :BackInterface{
                override fun back(): Fragment {
                    return  ArtistsFragment()
                }
            })
        }
        for (title in titlesList) {
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(title))
        }
        val vpFragmentAdapter =VpFragmentAdapter(this,fragmentList)
        mBinding.viewPager2.adapter=vpFragmentAdapter

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "单曲"
                1 -> tab.text = "歌手"
                else -> tab.text = "Third"
            }
        }.attach()


    }

    //开启沉浸式状态栏
    private fun iniActionBar() {
        transparentStatusBar(window,false)
    }

}