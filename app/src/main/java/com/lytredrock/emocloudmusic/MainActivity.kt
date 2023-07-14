package com.lytredrock.emocloudmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.lib.network.ViewModel

class MainActivity : AppCompatActivity() {
    //懒加载注入viewmodel
    val myViewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel.receiveMusicComments(186016)
        myViewModel.musicComments.observe(this){list->
            for(it in list ) {
                Log.d("TAG","(MainActivity.kt:20)-->> "+it.content)
            }
        }
    }

}