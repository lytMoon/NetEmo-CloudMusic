package com.lytredrock.model.player.fragment

import RvLyricsAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.model.player.R
import com.lytredrock.model.player.databinding.FragmentPageTwoBinding
import com.lytredrock.model.player.viewmodel.MusicPlayerViewModel
import java.lang.NullPointerException


class PageTwoFragment : Fragment() {


    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MusicPlayerViewModel::class.java]
    }

    //懒加载注入viewBinding
    private val mBinding: FragmentPageTwoBinding by lazy {
        FragmentPageTwoBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniLyrics()
        iniClick()

    }

    private fun iniClick() {
        mBinding.swapButton.setOnClickListener {
            replaceFragment(PageOneFragment())
        }
    }

    @SuppressLint("CommitTransaction", "ResourceType")
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireFragmentManager()
        val transaction = fragmentManager.beginTransaction()
        // 设置自定义动画
        transaction.setCustomAnimations(
            R.anim.fade_in,  // 新的Fragment进入动画
            R.anim.fade_out,  // 旧的Fragment退出动画
        )
        transaction.replace(R.id.fragment_place_holder, fragment)
        transaction.commit()

    }


    /**
     * 更新歌词的方法
     */
    private fun iniLyrics() {
        viewModel.musicLyricsInfo.observe(requireActivity()) {
            val lyricsList = lyricsWithChinese(it[0].lyric)
            try {
                mBinding.rvLyrics.adapter = RvLyricsAdapter(lyricsList, requireActivity())
                mBinding.rvLyrics.layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
            } catch (e: NullPointerException) {
                Log.d("NullPointerException", "(MVFragment.kt:52)-->> ")
            }


        }
    }


    /**
     * 下面是对歌词进行的加工方法，只保留歌词的文字部分
     */
    private fun lyricsWithChinese(str: String): MutableList<String> {
        val lyrics = str.trimIndent()
        val lines = lyrics.split("\n")
        val pattern = Regex("\\p{L}+(.*)") // 匹配一个或多个字母（Unicode 字符集）
        val lyricsList = mutableListOf<String>()
        for (line in lines) {
            val matcher = pattern.find(line)

            if (matcher != null) {
                lyricsList.add(matcher.value)
                Log.d("musicLyricsInfo", "(PageTwoFragment.kt:50)-->> ${matcher.value}")

            }
        }
        return lyricsList
    }
}