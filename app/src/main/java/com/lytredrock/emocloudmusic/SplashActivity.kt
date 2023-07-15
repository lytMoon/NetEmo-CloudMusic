package com.lytredrock.emocloudmusic

import BaseActivity
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.databinding.ActivitySplashBinding
import java.util.Timer
import java.util.TimerTask

class SplashActivity :BaseActivity() {
    private val myViewBinding: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myViewBinding.root)
        transparentStatusBar(window,false)
         var boolean=true

        myViewBinding.tvSkip.setOnClickListener {
            startActivity<MainActivity>()
            boolean=false }

        Glide.with(this).load(R.drawable.splash).into(myViewBinding.ivSplash)


        Timer().schedule(object : TimerTask() {
            @SuppressLint("SuspiciousIndentation")
            override fun run() {
                if (boolean)
                startActivity<MainActivity>()
            }

        },4000)
    }
}