package com.lytredrock.emocloudmusic.frgment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lytredrock.emocloudmusic.SongListActivity
import com.lytredrock.emocloudmusic.adapter.ChartAdapter
import com.lytredrock.emocloudmusic.adapter.RecommendSongListAdapter
import com.lytredrock.emocloudmusic.databinding.SongChartBinding
import com.lytredrock.emocloudmusic.viewmodel.SongChartViewModel

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/20 19:47
 */
class SongChartFragment :Fragment(){

    private val myViewModel by lazy { ViewModelProvider(this)[SongChartViewModel::class.java] }

   private var _binding: SongChartBinding? = null
   private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _binding = SongChartBinding.inflate(inflater, container, false)

        myViewModel.apply {
            getSongChartInInternet()
            chartLifeData.observe(viewLifecycleOwner){
                val myAdapter=ChartAdapter(it,requireActivity())
            binding.rvChart.adapter=myAdapter
                binding.rvChart.layoutManager= GridLayoutManager(requireContext(),2)

                myAdapter.setOnclick(object : ChartAdapter.ClickInterface {
                    override fun onImageviewClick(view: View, position: Int) {
                        val intent = Intent(requireContext(), SongListActivity::class.java)
                        intent.putExtra("id", it[position].id)
                        intent.putExtra("name",it[position].name)
                        startActivity(intent)
                    }

                })
            }
        }

        return binding.root
    }

    public override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}