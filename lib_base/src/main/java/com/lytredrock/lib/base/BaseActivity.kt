import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * description :BaseActivity封装
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/14 17:43
 */
open class BaseActivity():AppCompatActivity() {

    fun myToast(text:String,toastContext:Context){
        Toast.makeText(toastContext,text,Toast.LENGTH_SHORT)
    }
    inline fun <reified T: Activity> startActivity(){
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
    //扩展用于liveData便捷写法的函数
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline block: (T?) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            block(it)
        })
    }

    open fun initView(){}

    open fun initData(){}


    //如果想要状态栏文字为白色就传入true,黑色则传入false
    fun transparentStatusBar(window: Window,boolean:Boolean) {
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