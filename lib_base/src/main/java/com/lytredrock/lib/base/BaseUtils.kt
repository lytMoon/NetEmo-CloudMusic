package com.lytredrock.lib.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

/**
 * description ：因为我的模块在继承baseActivity的时候用不了aouter所以我把类里面的方法抽出来，
 * 因为主app已经继承了，所以baseActivity保留了，把里面的方法抽出来
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 14:32
 * version: 1.0
 */
object BaseUtils {

    fun myToast(text:String,toastContext: Context){
        Toast.makeText(toastContext,text, Toast.LENGTH_SHORT).show()
    }

    fun transparentStatusBar(window: Window, boolean:Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        var systemUiVisibility = window.decorView.systemUiVisibility
//        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN： 状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住
//        SYSTEM_UI_FLAG_LAYOUT_STABLE可以保持ui稳定
        systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        window.decorView.systemUiVisibility = systemUiVisibility
        window.statusBarColor = Color.TRANSPARENT

        setStatusBarTextColor(window,boolean )
    }

    fun setStatusBarTextColor(window: Window, light: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = if (light) { //白色文字
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else { //黑色文字
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.decorView.systemUiVisibility = systemUiVisibility
        }
    }
}