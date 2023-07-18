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
import androidx.lifecycle.ViewModelProvider
import com.lytredrock.model.login.netWorkUtils.NetWorkUtils
import com.lytredrock.model.login.adapter.BasePagerAdapter
import com.lytredrock.model.login.apiService.IVerifyCodeInfo
import com.lytredrock.model.login.apiService.MusicInfoCallBack
import com.lytredrock.model.login.apiService.PhoneNumCallBack
import com.lytredrock.model.login.apiService.qrCallBack
import com.lytredrock.model.login.databinding.ActivityLoginBinding
import com.lytredrock.model.login.databinding.ItemCodeLoginBinding
import com.lytredrock.model.login.databinding.ItemQrcodeLoginBinding
import com.lytredrock.model.login.loginData.CodeNum
import com.lytredrock.model.login.loginData.QRLast

class LoginActivity : BaseActivity() {

    private lateinit var loginAdapter: BasePagerAdapter
    private val titlesList = arrayListOf<String>()
    private val viewsList = arrayListOf<View>()
    private lateinit var phoneNum: String
    private lateinit var codeNumber: String


    private lateinit var qrCodeBinding: ItemQrcodeLoginBinding
    private lateinit var identifyBinding: ItemCodeLoginBinding
    //懒加载注入viewBinding
    private val mBinding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    //懒加载注入viewmodel
    val loginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        transparentStatusBar(window,false)
        iniTabLayout()
        onClick()
    }



    /**
     * 初始化tablayout和我们登录视图的绑定，使用viewpager（Androidx下的，adapter是pageAdapter）
     * 这里只是viewpager跟不同的view相互绑定，没有使用fragment，没有使用vp2
     * 我做的搜索model实现了viewpager2和fragment和tablayoutl联动。
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
            Log.d("TAG","(LoginActivity.kt:48)-->> "+ NetWorkUtils.receivedNumber)
            if (NetWorkUtils.receivedNumber!=null){
                qrCodeBinding.loginButton.isEnabled=true
                myToast("正在请求，未刷新请继续点击按钮",this)
                NetWorkUtils.receiveQRPic(NetWorkUtils.receivedNumber!!,object : MusicInfoCallBack {
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


        qrCodeBinding.loginButton.setOnClickListener {
            if (NetWorkUtils.receivedNumber!=null){
                NetWorkUtils.receiveQRState(NetWorkUtils.receivedNumber!!,object:qrCallBack{
                    override fun onRespond(data: QRLast) {
                        myToast(data.message,this@LoginActivity)
                        //下面跳转模块，同时
                    }

                    override fun onFailed(e: String) {

                        myToast(e,this@LoginActivity)
                        Log.d("TAG","(LoginActivity.kt:123)-->> $e")

                    }

                })

            }


        }

        /**
         * 实现手机号登录
         */
        identifyBinding.btButton.setOnClickListener {
             phoneNum = identifyBinding.tvPhoneNum.text.toString()
            if (phoneNum.matches(Regex("[0-9]+"))){
                myToast("发送成功，请不要频繁点击",this)
                identifyBinding.btLogin.isEnabled = true
                NetWorkUtils.receiveCodeNum(phoneNum,object: PhoneNumCallBack{
                    override fun onRespond(d: CodeNum) {


                    }

                    override fun onFailed(e: String) {

                    }

                })
            }
            else{
                myToast("请输入阿拉伯数字",this)
            }

        }

        identifyBinding.btLogin.setOnClickListener {
            codeNumber = identifyBinding.tvCode.text.toString()
            Log.d("codeNumber","(LoginActivity.kt:140)-->> $phoneNum,$codeNumber");
            if (::phoneNum.isInitialized&&::codeNumber.isInitialized){
                NetWorkUtils.receiveCodeState(phoneNum,codeNumber, object : IVerifyCodeInfo{
                    override fun onRespond(flag: Boolean) {
                        if (flag){
                            myToast("登录成功",this@LoginActivity)
                        }
                    }

                    override fun onFailed(e: String?) {
                        myToast("登录失败，请检查您的验证码",this@LoginActivity)
                        Log.d("TAG","(LoginActivity.kt:150)-->> $e")
                    }

                })
            }
            else{
               myToast("请正确填写信息",this)
            }


        }
    }

    /**
     * 把二维码图片的base64码转化为bitmap
     */
    private fun base64ToBitmap(qrCodeUrl: String) {
        val decode: ByteArray = Base64.decode(qrCodeUrl.split(",")[1], Base64.DEFAULT)
        val bitmap =  BitmapFactory.decodeByteArray(decode, 0, decode.size)
        qrCodeBinding.QRImage.setImageBitmap(bitmap)
    }


}