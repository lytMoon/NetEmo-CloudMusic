package com.lytredrock.emocloudmusic.adapter

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
import com.lytredrock.emocloudmusic.Download
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
class SongListAdapter(val data: List<Song>,private val activity: SongListActivity,) :
    RecyclerView.Adapter<SongListAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }
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

            more.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, more)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.collect-> {

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
                mv.visibility=View.GONE
            }
        }

    }
}