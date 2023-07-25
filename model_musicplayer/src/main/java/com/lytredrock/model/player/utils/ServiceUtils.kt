package com.lytredrock.model.player.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat

/**
 * description ：这里检查一下是否打开通知权限。
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/25 12:46
 * version: 1.0
 */
object ServiceUtils {
    fun identifyNotify(context: AppCompatActivity){
        val notificationManager = NotificationManagerCompat.from(context)
        val areNotificationsEnabled = notificationManager.areNotificationsEnabled()

        if (!areNotificationsEnabled) {
            AlertDialog.Builder(context)
                .setTitle("请求通知权限")
                .setMessage("为了能够接收通知，请授权通知权限(前台Service通知一定要打开，最好是都打开)。")
                .setPositiveButton("授权") { _, _ ->
                    // 用户点击了授权按钮，启动系统的通知设置界面
                    val intent = Intent()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    } else {
                        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        intent.putExtra("app_package", context.packageName)
                        intent.putExtra("app_uid", context.applicationInfo.uid)
                    }
                    context.startActivity(intent)
                }
                .setNegativeButton("取消", null)
                .show()
        }
    }
}