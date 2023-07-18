package com.lytredrock.model.research.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.model.research.R
import com.lytredrock.model.research.musicdata.Song

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/18 15:57
 * version: 1.0
 */
class RvSongAdapter(val data: List<Song>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RvSongAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }


    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val musicName: TextView = root.findViewById<TextView>(R.id.tv_music_name)
        val musicInfo:TextView=root.findViewById(R.id.tv_music_Info)
        val artistName :TextView = root.findViewById<TextView>(R.id.tv_artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_song, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            musicName.text = itemData.name
            musicInfo.text = itemData.album.name
            artistName.text=itemData.artists.get(0).name
//            val temp = itemData.
//            Glide.with(activity).load(temp).into(ivfind)
        }
//        holder.artistName.setOnClickListener {
//            clickInterface?.onTitleClick(it, position)
//        }
    }
}