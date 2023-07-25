package com.lytredrock.model.player.ui

import android.app.ActivityManager
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


/**
 * 这里我们使用service来播放音乐。
 */
class MusicService : Service() {


    private lateinit var player: ExoPlayer
    private lateinit var url: String
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun startPlay() {
            if (player.playWhenReady) {
                player.clearMediaItems()
                player.addMediaItem(MediaItem.fromUri(url))
                player.prepare()
                player.playWhenReady = true
            } else {
                player.addMediaItem(MediaItem.fromUri(url))
                player.prepare()
                player.playWhenReady = true
            }
            Log.d("MusicService", "(MusicService.kt:15)-->> 开始播放音乐")
        }

        fun getCurrentPosition(): Int {
            return player.currentPosition.toInt()
        }

        fun getBufferedPosition(): Int {
            return player.currentPosition.toInt()
        }

        fun getDuration(): Int {
            return player.duration.toInt()
        }

        fun seekTo(position: Long) {
            player.seekTo(position)
            Log.d("position", "(MusicPlayerActivity.kt:201)-->> $position");

        }

        fun stop() {
            player.playWhenReady = false
        }

        fun start() {
            player.playWhenReady = true

        }

        fun isPlaying(): Boolean {
            return player.isPlaying
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
        player.stop()
        player.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        url = intent?.getStringExtra("musicUrl").toString()
        Log.d("555525", "(MusicService.kt:97)-->> $url");
        return super.onStartCommand(intent, flags, startId)
    }


}





