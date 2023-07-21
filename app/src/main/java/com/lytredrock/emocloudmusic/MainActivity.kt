package com.lytredrock.emocloudmusic

import BaseActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.lytredrock.emocloudmusic.adapter.FragmentAdapter
import com.lytredrock.emocloudmusic.databinding.ActivityMainBinding
import com.lytredrock.emocloudmusic.frgment.BackFragment
import com.lytredrock.emocloudmusic.frgment.HotFragment
import com.lytredrock.emocloudmusic.frgment.FindFragment
import com.lytredrock.emocloudmusic.frgment.MineFragment


class MainActivity : BaseActivity() {
    private val myViewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        transparentStatusBar(window, false)
        myViewBinding.ivSlideMenu.bringToFront()
        myViewBinding.ivSlideMenu.setOnClickListener {
            myViewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        var fragments = ArrayList<BackFragment>()
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
                return HotFragment()
            }
        })
        myViewBinding.vp2.adapter = FragmentAdapter(this, fragments)
        myViewBinding.vp2.isUserInputEnabled = false

        myViewBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.find -> myViewBinding.vp2.currentItem = 0
                R.id.mine -> myViewBinding.vp2.currentItem = 1
                R.id.community -> myViewBinding.vp2.currentItem = 2
            }
            return@setOnItemSelectedListener true

        }
    }
}


