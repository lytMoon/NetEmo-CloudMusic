package com.lytredrock.emocloudmusic.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.Download
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.SingerSong
import com.lytredrock.emocloudmusic.data.SingSongData

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/22 10:45
 */
class SingerSongAdapter(val data: List<SingSongData.SingerSong>, private val activity: SingerSong) :
    RecyclerView.Adapter<SingerSongAdapter.InnerHolder>() {
    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    inner class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val mv = root.findViewById<ImageView>(R.id.iv_singerMv)
        val songName = root.findViewById<TextView>(R.id.tv_songName)
        val artist = root.findViewById<TextView>(R.id.tv_Sing)
        val more = root.findViewById<ImageView>(R.id.iv_more)

        init {
            mv.setOnClickListener {
                ARouter.getInstance()
                    .build("/mv/mvPlay")
                    .withString("mvUid", data[absoluteAdapterPosition].mv.toString())
                    .withString("mvName", data[absoluteAdapterPosition].name)
                    .navigation()
            }

            itemView.setOnClickListener {
                ARouter.getInstance()
                    .build("/music/musicPlay")
                    .withString("musicId", data[absoluteAdapterPosition].id.toString())
                    .navigation()
            }

            more.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, more)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.collect -> {
                            val intent = Intent(
                                itemView.context,
                                com.lytredrock.emocloudmusic.Collect::class.java
                            )
                            intent.putExtra("name", data[absoluteAdapterPosition].name)
                            intent.putExtra("author", data[absoluteAdapterPosition].ar[0].name)
                            intent.putExtra("id", data[absoluteAdapterPosition].id)
                            itemView.context.startActivity(intent)
                            true
                        }

                        R.id.download -> {
                            val intent = Intent(itemView.context, Download::class.java)
                            intent.putExtra("id", data[absoluteAdapterPosition].id)
                            intent.putExtra("name", data[absoluteAdapterPosition].name)
                            intent.putExtra("author", data[absoluteAdapterPosition].ar[0].name)
                            itemView.context.startActivity(intent)
                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_singer_song, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            songName.text = itemData.name
            when (itemData.ar.size) {
                1 -> artist.text = "${itemData.ar[0].name}-${itemData.al.name}"
                2 -> artist.text =
                    "${itemData.ar[0].name}/${itemData.ar[1].name}-${itemData.al.name}"

                else -> artist.text =
                    "${itemData.ar[0].name}/${itemData.ar[1].name}/${itemData.ar[2].name}-${itemData.al.name}"
            }
            if (itemData.mv != 0) {
                Glide.with(activity).load(R.drawable.ic_broadcast).into(mv)
            } else {
                mv.visibility = View.GONE
            }
        }

    }
}