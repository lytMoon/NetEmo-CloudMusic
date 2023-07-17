package com.lytredrock.model.research

import BaseActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.model.research.databinding.ResearchMainBinding

class ResearchActivity : BaseActivity() {

    //懒加载注入viewBinding
    private val mBinding: ResearchMainBinding by lazy { ResearchMainBinding.inflate(layoutInflater) }
    //懒加载注入viewmodel
    val loginViewModel by lazy {
        ViewModelProvider(this)[ResearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.research_main)
        iniActionBar()
    }
   //开启沉浸式状态栏
    private fun iniActionBar() {
        transparentStatusBar(window,false)
    }

}