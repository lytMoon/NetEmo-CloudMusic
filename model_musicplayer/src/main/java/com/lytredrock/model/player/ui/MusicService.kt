package com.lytredrock.model.player.ui

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import com.lytredrock.model.player.R


/**
 * 这里我们使用service来播放音乐。
 */
class MusicService : Service() {


    private lateinit var player: ExoPlayer
    private lateinit var url: String
    private lateinit var isTop: String
    private lateinit var musicName: String
    private lateinit var musicAuthorName: String
    private lateinit var musicImgUrl: String
    private  var justTest ="0"
    var tag: String = "0"
    private val binder = MusicBinder()

    inner class MusicBinder : Binder(), Player.Listener {

        fun startPlay() {
            player.addListener(this)

            /**
             * 判断是不是点的上面的前台服务，如果不是就重新播放音乐（肯定切换新的音乐了）
             */
            if (isTop != "1") {
                player.clearMediaItems()
                player.addMediaItem(MediaItem.fromUri(url))
                player.prepare()
                player.playWhenReady = true
            }
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

        fun getMusicImgUrl(): String {
            return musicImgUrl
        }

        fun getMusicName(): String {
            return musicName
        }

        fun getMusicAuthor(): String {
            return musicAuthorName
        }

        fun getIsTop(): String {
            return isTop
        }


        override fun onPlaybackStateChanged(playbackState: @Renderer.State Int) {
            super.onPlaybackStateChanged(playbackState)
            if (playbackState == Player.STATE_ENDED) {
                player.seekToDefaultPosition()
                player.play()
            }
        }


    }


    /**
     * 通过onBind（）方法，activity才能和service进行通信(创建自己的binder类，让activity持有引用)
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
         * 开启前台服务，更安全
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
        //虽然设置了，但是很遗憾，service也绑定了主app，一开始就初始化了，if相当于没有
        if(justTest=="0"){
            val intent = Intent(this, MusicPlayerActivity::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val notification = NotificationCompat.Builder(this, "my_service")
                .setContentTitle("emoCloud")
                .setContentText("音乐播放服务持续生效中")
                .setSmallIcon(R.drawable.musiclogo)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.musiclogo))
                .setContentIntent(pi)
                .setOngoing(true)  // 设置通知为持续显示
                .build()
            startForeground(1, notification)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        url = intent?.getStringExtra("musicUrl").toString()
        isTop = intent?.getStringExtra("is_ok").toString()
        tag = intent?.getStringExtra("flag").toString()
        musicName = intent?.getStringExtra("musicName").toString()
        musicAuthorName = intent?.getStringExtra("musicAuthorName").toString()
        musicImgUrl = intent?.getStringExtra("musicImgUrl").toString()
        justTest = intent?.getStringExtra("justOK").toString()
        Log.d("555525", "(MusicService.kt:97)-->> $url");
        Log.d("555526", "(MusicService.kt:97)-->> $isTop");
        Log.d("555527", "(MusicService.kt:97)-->> $tag");
        Log.d("555528", "onStartCommand: ${musicName}$musicAuthorName$musicImgUrl")
        Log.d("555529", "onStartCommand: $justTest")
        return super.onStartCommand(intent, flags, startId)

    }


}








