package com.lytredrock.model.login

import BaseActivity
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import com.lytredrock.lib.network.netWorkUtils.NetWorkUtils
import com.lytredrock.model.login.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var webView: WebView

    //懒加载注入databinding
    private val mBinding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        transparentStatusBar(window,false)
        iniBind()
        onClick()
    }

    private fun iniBind() {
        webView = mBinding.loginWebView
    }


    /**
     * 操作说明
     */
    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    private fun onClick() {
        /**
         * 弹窗口信息
         */
        mBinding.btMsg.setOnClickListener{
            // 创建一个Dialog对象
            val dialog = Dialog(this)
           // 设置Dialog的内容视图
            val textView = TextView(this)
            textView.text = "这是一段关于登录使用的文本信息！\n请您好好看一下\n正常操作：先点击加载按钮，扫码完成后再点击登录按钮\n1.请不要频繁点击刷新二维码按钮，可能会被网易云限制\n2.请您尽快扫码，时间过久的话二维码可能会失效\n3.登录按钮不要频繁点击"
            dialog.setContentView(textView)
           // 显示Dialog
            dialog.show()
        }
        /**
         * 加载二维码，分两个步骤，一个是得到key，一个得到图片信息
         */
        mBinding.loadPic.setOnClickListener {
            NetWorkUtils.ReceiveQRKey()
            Log.d("TAG","(LoginActivity.kt:48)-->> "+NetWorkUtils.receivedNumber)
            if (NetWorkUtils.receivedNumber!=null){
                myToast("请求成功，请等待下一步提示",this)
                NetWorkUtils.receiveQRPic(NetWorkUtils.receivedNumber!!)
                Log.d("TAG","(LoginActivity.kt:52)-->> "+NetWorkUtils.qrUrl)
                if(NetWorkUtils.qrUrl!=null){
                    webView.webViewClient = WebViewClient()
                    // 获取 WebView 的设置
                    val settings = webView.settings
                    // 设置 User-Agent 标识,通过扫码登录
                    settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
                    webView.settings.javaScriptEnabled = true
                    webView.loadUrl(NetWorkUtils.qrUrl!!)
                }
                else{
                    myToast("请再点击一下加载按钮",this)
                }
            }
            else{
                myToast("请再点击一下加载按钮",this)

            }

        }





    }
}