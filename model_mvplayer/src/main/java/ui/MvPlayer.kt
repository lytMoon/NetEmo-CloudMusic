package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.lytredrock.lib.base.BaseUtils.transparentStatusBar
import com.lytredrock.model.mvplayer.R
import viewmodel.MvPlayViewModel

@Route(path = "/mv/mvPlay")
class MvPlayer : AppCompatActivity() {

    //懒加载注入viewmodel
    private val myViewModel by lazy {
        ViewModelProvider(this)[MvPlayViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mv_player)
        iniBar()
        myViewModel.getMvUrl("5302569")
        myViewModel.mvUrlData.observe(this) {
            Log.d("mvUrlData", "(MvPlayer.kt:25)-->> ${it[0]}")
        }


    }

    //开启沉浸式状态栏
    private fun iniBar() {
        transparentStatusBar(window, false)
    }
}