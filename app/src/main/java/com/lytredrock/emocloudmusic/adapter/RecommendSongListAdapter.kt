package com.lytredrock.emocloudmusic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.data.Result

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/18 16:55
 */
class RecommendSongListAdapter(val data: List<Result>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RecommendSongListAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onImageviewClick(view: ImageView, position: Int)
    }

    public fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    inner class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val recommendSongList = root.findViewById<ImageView>(R.id.iv_recommendSongList)
        val tvrecommendSongList = root.findViewById<TextView>(R.id.tv_recommendSongList)

        init {
            recommendSongList.setOnClickListener {
                clickInterface?.onImageviewClick(recommendSongList, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_recommendsonglist, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            tvrecommendSongList.text = itemData.name
            Glide.with(activity).load(itemData.picUrl).into(recommendSongList)
        }
    }
}