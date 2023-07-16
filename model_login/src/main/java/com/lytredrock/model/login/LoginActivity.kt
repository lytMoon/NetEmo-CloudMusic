package com.lytredrock.model.login

import BaseActivity
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lytredrock.lib.network.apiService.MusicInfoCallBack
import com.lytredrock.lib.network.netWorkUtils.NetWorkUtils
import com.lytredrock.model.login.adapter.BasePagerAdapter
import com.lytredrock.model.login.databinding.ActivityLoginBinding
import com.lytredrock.model.login.databinding.ItemCodeLoginBinding
import com.lytredrock.model.login.databinding.ItemQrcodeLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var loginAdapter: BasePagerAdapter
    private val titlesList = arrayListOf<String>()
    private val viewsList = arrayListOf<View>()

    private lateinit var qrCodeBinding: ItemQrcodeLoginBinding
    private lateinit var identifyBinding: ItemCodeLoginBinding
    //懒加载注入databinding
    private val mBinding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        transparentStatusBar(window,false)
        iniTabLayout()
        onClick()
    }


    /**
     * 初始化tablayout和我们登录视图的绑定，使用viewpager（Androidx下的，adapter是pageAdapter）
     */
    private fun iniTabLayout() {
        val qrCodeView: View = LayoutInflater.from(this).inflate(R.layout.item_qrcode_login, null)
        val identifyView: View = LayoutInflater.from(this).inflate(R.layout.item_code_login, null)

        qrCodeBinding = ItemQrcodeLoginBinding.bind(qrCodeView)
        identifyBinding = ItemCodeLoginBinding.bind(identifyView)

        viewsList.let {
            it.add(qrCodeView)
            it.add(identifyView)
        }
        titlesList.let {
            it.add("二维码登录")
            it.add("手机登录")
        }
        loginAdapter = BasePagerAdapter(viewsList, titlesList)
        for (title in titlesList) {
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(title))
        }
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        mBinding.viewPager.adapter = loginAdapter
    }


    /**
     * 操作说明
     */
    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    private fun onClick() {
        /**
         * 弹窗口信息
         */
        qrCodeBinding.btMsg.setOnClickListener{
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
        qrCodeBinding.loadPic.setOnClickListener {
            NetWorkUtils.ReceiveQRKey()
            Log.d("TAG","(LoginActivity.kt:48)-->> "+NetWorkUtils.receivedNumber)
            if (NetWorkUtils.receivedNumber!=null){
                myToast("正在请求，未刷新请继续点击按钮",this)
                NetWorkUtils.receiveQRPic(NetWorkUtils.receivedNumber!!,object : MusicInfoCallBack{
                    override fun onRespond(qrimg: String) {
                        base64ToBitmap(qrimg)
                    }
                    override fun onFailed(e:String) {
                       Log.d("TAG","(LoginActivity.kt:65)-->>网络请求失败了 $e");
                    }
                })
            }
            else{
                myToast("请再次点击按钮",this)
            }
        }
    }

    /**
     * 把二维码图片的base64吗转化为bitmap
     */
    private fun base64ToBitmap(qrCodeUrl: String) {
        val decode: ByteArray = Base64.decode(qrCodeUrl.split(",")[1], Base64.DEFAULT)
        val bitmap =  BitmapFactory.decodeByteArray(decode, 0, decode.size)
        qrCodeBinding.QRImage.setImageBitmap(bitmap)
    }
}