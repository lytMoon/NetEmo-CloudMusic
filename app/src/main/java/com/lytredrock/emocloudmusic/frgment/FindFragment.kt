package com.lytredrock.emocloudmusic.frgment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.carousel.CarouselLayoutManager
import com.lytredrock.emocloudmusic.SongListActivity
import com.lytredrock.emocloudmusic.adapter.BannerAdapter
import com.lytredrock.emocloudmusic.adapter.MainRvAdapter
import com.lytredrock.emocloudmusic.adapter.RecommendSongListAdapter
import com.lytredrock.emocloudmusic.databinding.FragmentFindBinding
import com.lytredrock.emocloudmusic.viewmodel.FindFragmentViewModel
import com.lytredrock.model.research.SEARCH_AROUTER
import java.util.Timer
import java.util.TimerTask

/**
 * description ： 发现页面的fragment
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/15 19:50
 */
class FindFragment : Fragment() {

    private val myViewModel by lazy { ViewModelProvider(this)[FindFragmentViewModel::class.java] }

    private val binding: FragmentFindBinding by lazy { FragmentFindBinding.inflate(layoutInflater) }

    private val timer = Timer()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val fragments = ArrayList<BackFragment>()
        myViewModel.apply {
            getBannerInFragment(1)
            bannerLifeData.observe(viewLifecycleOwner) {
                for (element in it) {
                    val itemFragment = ItemFragment()
                    val bundle = Bundle()
                    bundle.putString("data", element.pic)
                    itemFragment.arguments = bundle
                    fragments.add(object : BackFragment {
                        override fun back(): Fragment {
                            return itemFragment
                        }
                    })
                }
                binding.vpFind.adapter = BannerAdapter(requireActivity(), fragments)
            }
        }

        binding.cardSearch.setOnClickListener {
            ARouter.getInstance().build(SEARCH_AROUTER).navigation()
        }

        myViewModel.apply {
            getRecommendSongLIstInFragment()
            recommendSongListLifeData.observe(viewLifecycleOwner) {
                val myAdapter = RecommendSongListAdapter(it, requireActivity())
                binding.rvRecommendSongList.adapter = myAdapter
//              使用了CarouselLayoutManager()，实现了跑马灯的效果
                binding.rvRecommendSongList.layoutManager = CarouselLayoutManager()
//                设置推荐歌单的点击事件
                myAdapter.setOnclick(object : RecommendSongListAdapter.ClickInterface {
                    override fun onImageviewClick(view: ImageView, position: Int) {
//                        元素共享动画
                        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            view,
                            "ShareElement1"
                        ).toBundle()
                        val intent = Intent(requireContext(), SongListActivity::class.java)
                        intent.putExtra("id", it[position].id)
                        intent.putExtra("name", it[position].name)
                        intent.putExtra("photo", it[position].picUrl)
                        startActivity(intent, bundle)
                    }

                })
            }
        }



        myViewModel.apply {
            getBallInFragment()
            dataLifeData.observe(viewLifecycleOwner) {
                binding.rvFind.adapter = MainRvAdapter(it, requireActivity())
                binding.rvFind.layoutManager =
                    GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
            }
        }

// 利用timer实现了banner的自动轮播
        timer.schedule(object : TimerTask() {
            @SuppressLint("SuspiciousIndentation")
            override fun run() {
                when (binding.vpFind.currentItem) {

                    fragments.size - 1 -> activity?.runOnUiThread { binding.vpFind.currentItem = 0 }

                    else -> activity?.runOnUiThread {
                        binding.vpFind.currentItem = ++binding.vpFind.currentItem
                    }
                }
            }

        }, 2000, 3000)


        return binding.root
    }

    public override fun onViewCreated(
        view: android.view.View, savedInstanceState: android.os.Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

}