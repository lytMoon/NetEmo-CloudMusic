package com.lytredrock.model.player.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.lytredrock.model.player.R
import kotlin.properties.Delegates


/**
 * Service没有完成，很遗憾，
 */
class MusicService : Service() {


    private lateinit var player: ExoPlayer
    private lateinit var url: String
    private var currentPosition by Delegates.notNull<Int>()
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun startPlay() {
            Log.d("MusicService", "(MusicService.kt:15)-->> 开始播放音乐")
            //填充媒体数据
            player.addMediaItem(MediaItem.fromUri(url))
            //准备播放
            player.prepare()
            //准备完成就开始播放
            player.seekTo(currentPosition.toLong())
            Log.d("TAG","(MusicService.kt:41)-->> ");
            player.playWhenReady = true
        }

        fun getProgress() {
        }

    }


    /**
     * 通过onBind（）方法，activity才能和service进行通信
     */
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        iniNotify()


    }

    private fun iniNotify() {
        /**
         * 开启前台服务，更安全,
         */
        Log.d("MusicService", "(MusicService.kt:15)-->> onCreate ");
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "my_service",
                "前台Service通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MusicPlayerActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, "my_service")
            .setContentTitle("emoCloud")
            .setSmallIcon(R.drawable.musiclogo)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.musiclogo))
            .setContentIntent(pi)
            .build()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        url= intent?.getStringExtra("musicUrl").toString()
        currentPosition= intent?.getIntExtra("currentPosition",0)!!
        Log.d("555525","(MusicService.kt:97)-->> $url");
        Log.d("855588","(MusicService.kt:97)-->> $currentPosition");
        return super.onStartCommand(intent, flags, startId)
    }
}





