package com.lytredrock.model.research

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.lytredrock.lib.base.BaseUtils
import com.lytredrock.lib.base.BaseUtils.myToast
import com.lytredrock.model.research.adapter.VpFragmentAdapter
import com.lytredrock.model.research.apiService.BackInterface
import com.lytredrock.model.research.databinding.ResearchMainBinding
import com.lytredrock.model.research.fragments.ArtistsFragment
import com.lytredrock.model.research.fragments.MVFragment
import com.lytredrock.model.research.fragments.SongFragment
import com.lytredrock.model.research.viewModel.ArtistsViewModel
import com.lytredrock.model.research.viewModel.MVViewmodel
import com.lytredrock.model.research.viewModel.SongViewModel

@Route(path =SEARCH_AROUTER)
class ResearchActivity : AppCompatActivity() {
    private val titlesList = arrayListOf<String>()
    private val fragmentList = arrayListOf<BackInterface>()
    //懒加载注入viewBinding
    private val mBinding: ResearchMainBinding by lazy { ResearchMainBinding.inflate(layoutInflater) }
    //懒加载注入viewmodel
    private val songViewModel by lazy {
        ViewModelProvider(this)[SongViewModel::class.java]
    }
    private val artistViewModel by lazy {
        ViewModelProvider(this)[ArtistsViewModel::class.java]
    }
    private val mvViewmodel by lazy {
        ViewModelProvider(this)[MVViewmodel::class.java]
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
                songViewModel.getSongInfo(key)
                artistViewModel.getArtistsInfo(key)
                mvViewmodel.getMVInfo(key)
            }
            Log.d("tvSearch","(ResearchActivity.kt:41)-->> $key");
        }
    }

    private fun iniTest() {

    }

    private fun iniTabLayout() {

        fragmentList.let {
            it.add( object : BackInterface {
                override fun back(): Fragment {
                    return   SongFragment()
                }
            })
            it.add(object : BackInterface {
                override fun back(): Fragment {
                    return  ArtistsFragment()
                }
            })
            it.add(object : BackInterface {
                override fun back(): Fragment {
                    return  MVFragment()
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
                else -> tab.text = "MV"
            }
        }.attach()
    }
    //开启沉浸式状态栏
    private fun iniActionBar() {
        BaseUtils.transparentStatusBar(window,false)
    }
}