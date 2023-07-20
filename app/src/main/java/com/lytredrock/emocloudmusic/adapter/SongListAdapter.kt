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
import com.lytredrock.emocloudmusic.SongListActivity
import com.lytredrock.emocloudmusic.data.Data
import com.lytredrock.emocloudmusic.data.Song

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/19 15:31
 */
class SongListAdapter(val data: List<Song>,private val activity: SongListActivity) :
    RecyclerView.Adapter<SongListAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name=root.findViewById<TextView>(R.id.tv_itemSongList)
        val Singer=root.findViewById<TextView>(R.id.tv_Singer)
        val song=root.findViewById<ImageView>(R.id.iv_song)
        val mv=root.findViewById<ImageView>(R.id.iv_mv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_songlist, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            Glide.with(activity).load(itemData.al.picUrl).into(song)
            Singer.text=itemData.ar[0].name
          name.text=itemData.name
            if(itemData.mv!=0){
                Glide.with(activity).load(R.drawable.ic_broadcast).into(mv)
            }
        }

    }
}