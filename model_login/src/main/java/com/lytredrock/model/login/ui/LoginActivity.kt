package com.lytredrock.model.login.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Path
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lytredrock.lib.base.BaseUtils.myToast
import com.lytredrock.lib.base.BaseUtils.transparentStatusBar
import com.lytredrock.model.login.R
import com.lytredrock.model.login.networkutils.NetWorkUtils
import com.lytredrock.model.login.adapter.BasePagerAdapter
import com.lytredrock.model.login.apiservice.IVerifyCodeInfo
import com.lytredrock.model.login.apiservice.MusicInfoCallBack
import com.lytredrock.model.login.apiservice.PhoneNumCallBack
import com.lytredrock.model.login.apiservice.qrCallBack
import com.lytredrock.model.login.databinding.ActivityLoginBinding
import com.lytredrock.model.login.databinding.ItemCodeLoginBinding
import com.lytredrock.model.login.databinding.ItemQrcodeLoginBinding
import com.lytredrock.model.login.logindata.CodeNum
import com.lytredrock.model.login.logindata.QRLast


/**
 * 登录类里面没有什么可以储存的数据，我这里的话没有使用viewModel接收数据，直接通过回调传过来返回的结果。
 * 做出判断（遗憾的是，尽管返回的数据显示你已经登录成功，但是始终是游客登录）
 *这里由于没有使用viewModel,目前我不知道如果不使用回调如何把返回的String类型的数据传过来并且对布尔类型的数值进行判断
 */

@Route(path = "/login/start")
class LoginActivity : AppCompatActivity() {

    private lateinit var loginAdapter: BasePagerAdapter
    private val titlesList = arrayListOf<String>()
    private val viewsList = arrayListOf<View>()
    private lateinit var phoneNum: String
    private lateinit var codeNumber: String
    private lateinit var qrCodeBinding: ItemQrcodeLoginBinding
    private lateinit var identifyBinding: ItemCodeLoginBinding
    //懒加载注入viewBinding
    private val mBinding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        transparentStatusBar(window, false)
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
        qrCodeBinding.btMsg.setOnClickListener {

            // 创建一个Dialog对象
            val dialog = Dialog(this)
            // 设置Dialog的内容视图
            val textView = TextView(this)
            textView.text =
                "这是一段关于登录使用的文本信息！\n请您好好看一下\n正常操作：先点击加载按钮，扫码完成后再点击登录按钮\n1.请不要频繁点击刷新二维码按钮，可能会被网易云限制\n2.请您尽快扫码，时间过久的话二维码可能会失效\n3.登录按钮不要频繁点击"
            dialog.setContentView(textView)
            // 显示Dialog
            dialog.show()
        }
        /**
         * 加载二维码，分两个步骤，一个是得到key，一个得到图片信息
         */
        qrCodeBinding.loadPic.setOnClickListener {
            NetWorkUtils.ReceiveQRKey()
            Log.d("TAG", "(LoginActivity.kt:48)-->> " + NetWorkUtils.receivedNumber)
            if (NetWorkUtils.receivedNumber != null) {
                qrCodeBinding.loginButton.isEnabled = true
                myToast("正在请求，未刷新请继续点击按钮", this)
                NetWorkUtils.receiveQRPic(NetWorkUtils.receivedNumber!!,
                    object : MusicInfoCallBack {
                        override fun onRespond(qrimg: String) {
                            base64ToBitmap(qrimg)
                        }

                        override fun onFailed(e: String) {
                            Log.d("TAG", "(LoginActivity.kt:65)-->>网络请求失败了 $e");
                        }
                    })
            } else {
                myToast("请再次点击按钮", this)
            }
        }


        qrCodeBinding.loginButton.setOnClickListener {
            if (NetWorkUtils.receivedNumber != null) {
                NetWorkUtils.receiveQRState(NetWorkUtils.receivedNumber!!, object : qrCallBack {
                    @SuppressLint("CommitPrefEdits")
                    override fun onRespond(data: QRLast) {
                        myToast(data.message, this@LoginActivity)
                        myToast("登录成功(很遗憾由于某种原因，只能游客登录)", this@LoginActivity)
                        finish()
                    }

                    override fun onFailed(e: String) {

                        myToast(e, this@LoginActivity)
                        Log.d("TAG", "(LoginActivity.kt:123)-->> $e")
                    }
                })

            }


        }

        /**
         * 实现手机号登录
         */
        identifyBinding.btButton.setOnClickListener {
            phoneNum = identifyBinding.tvPhoneNum.text.toString()
            if (phoneNum.matches(Regex("[0-9]+"))) {
                myToast("发送成功，请不要频繁点击", this)
                identifyBinding.btLogin.isEnabled = true
                NetWorkUtils.receiveCodeNum(phoneNum, object : PhoneNumCallBack {
                    override fun onRespond(d: CodeNum) {
                    }
                    override fun onFailed(e: String) {
                    }
                })
            } else {
                myToast("请输入阿拉伯数字", this)
            }
        }

        identifyBinding.btLogin.setOnClickListener {
            codeNumber = identifyBinding.tvCode.text.toString()
            Log.d("codeNumber", "(LoginActivity.kt:140)-->> $phoneNum,$codeNumber");
            if (::phoneNum.isInitialized && ::codeNumber.isInitialized) {
                NetWorkUtils.receiveCodeState(phoneNum, codeNumber, object : IVerifyCodeInfo {
                    @SuppressLint("CommitPrefEdits")
                    override fun onRespond(flag: Boolean) {
                        if (flag) {
                            myToast("登录成功(很遗憾由于某种原因，只能游客登录)", this@LoginActivity)
                            finish()
                        }
                    }
                    override fun onFailed(e: String?) {
                        myToast("登录失败，请检查您的验证码", this@LoginActivity)
                        Log.d("TAG", "(LoginActivity.kt:150)-->> $e")
                    }
                })
            } else {
                myToast("请正确填写信息", this)
            }
        }
    }

    /**
     * 把二维码图片的base64码转化为bitmap
     */
    private fun base64ToBitmap(qrCodeUrl: String) {
        val decode: ByteArray = Base64.decode(qrCodeUrl.split(",")[1], Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.size)
        qrCodeBinding.QRImage.setImageBitmap(bitmap)
    }


}