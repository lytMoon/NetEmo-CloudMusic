package com.lytredrock.model.research.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.lytredrock.model.research.R
import com.lytredrock.model.research.musicdata.ArtistSong
import com.lytredrock.model.research.ui.ArtistShowActivity

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/26 22:16
 * version: 1.0
 */
class RvArtistSongAdapter(val data: List<ArtistSong>,private val activity: ArtistShowActivity) :
    RecyclerView.Adapter<RvArtistSongAdapter.InnerHolder>() {

    inner class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name=root.findViewById<TextView>(R.id.tv_itemSongList)
        val Singer=root.findViewById<TextView>(R.id.tv_Singer)
        val song=root.findViewById<ImageView>(R.id.iv_song)
        val mv=root.findViewById<ImageView>(R.id.iv_mv)
        val more=root.findViewById<ImageView>(R.id.iv_more1)
        init {
            mv.setOnClickListener {
                ARouter.getInstance()
                    .build("/mv/mvPlay")
                    .withString("mvUid",data[absoluteAdapterPosition].mv.toString())
                    .withString("mvName",data[absoluteAdapterPosition].name)
                    .navigation()
            }
            itemView.setOnClickListener {
                ARouter.getInstance()
                    .build("/music/musicPlay")
                    .withString("musicId",data[absoluteAdapterPosition].id.toString())
                    .navigation()
                Log.d("setOnClickListener",data[absoluteAdapterPosition].id.toString() )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_songlist, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            Glide.with(activity).load(itemData.al.picUrl).into(song)
            Singer.text=itemData.ar[0].name
            name.text=itemData.name
            if(itemData.mv!=0){
                Glide.with(activity).load(R.drawable.ic_broadcast).into(mv)
            }else{
                mv.visibility= View.GONE
            }
        }

    }
}