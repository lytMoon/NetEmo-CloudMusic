package com.lytredrock.emocloudmusic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.data.HotSongListData
import com.lytredrock.emocloudmusic.data.SongChart

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/22 16:08
 */
class HotSongListAdapter(val data: List<HotSongListData.Playlists>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<HotSongListAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onImageviewClick(view: View, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val cover=root.findViewById<ImageView>(R.id.iv_hotSongList)
        val name=root.findViewById<TextView>(R.id.tv_hotSongListName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_hotsonglist, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            Glide.with(activity).load(itemData.coverImgUrl).into(cover)
            name.text=itemData.name
        }
        holder.cover.setOnClickListener{
            clickInterface?.onImageviewClick(it, position)
        }
    }
}