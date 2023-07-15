package com.lytredrock.emocloudmusic

import BaseActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.emocloudmusic.databinding.ActivityMainBinding
import com.lytredrock.emocloudmusic.databinding.ActivitySplashBinding
import com.lytredrock.emocloudmusic.frgment.CommunityFragment
import com.lytredrock.emocloudmusic.frgment.FindFragment
import com.lytredrock.emocloudmusic.frgment.MineFragment
import com.lytredrock.lib.network.BaseViewModel

class MainActivity : BaseActivity(){
    private val myViewBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        transparentStatusBar(window,false)
        var fragments= ArrayList<BackFragment>()
        fragments.add(object : BackFragment {
            override fun back(): Fragment {
               return FindFragment()
            }
        })
        fragments.add(object : BackFragment {
            override fun back(): Fragment {
                return MineFragment()
            }
        })
        fragments.add(object : BackFragment {
            override fun back(): Fragment {
                return CommunityFragment()
            }
        })
        myViewBinding.vp2.adapter=FragmentAdapter(this,fragments)
        myViewBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.find-> myViewBinding.vp2.currentItem = 0
                R.id.mine -> myViewBinding.vp2.currentItem = 1
                R.id.community -> myViewBinding.vp2.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }

    }

}