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
import com.lytredrock.emocloudmusic.Download
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.SingerSong
import com.lytredrock.emocloudmusic.data.CollectData
import com.lytredrock.emocloudmusic.data.SongChart

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 13:48
 */

class DownloadAdapter(val data: List<CollectData>,private val activity: Download) :
    RecyclerView.Adapter<DownloadAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onImageviewClick(view: ImageView, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    inner class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
       val name=root.findViewById<TextView>(R.id.tv_downloadName)
        val author=root.findViewById<TextView>(R.id.tv_downloadAuthor)
        val mv=root.findViewById<ImageView>(R.id.iv_downloadMv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_download, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            name.text=itemData.name
            author.text=itemData.author
            if(itemData.mv!=0){
                Glide.with(activity).load(R.drawable.ic_broadcast).into(mv)
            }else{
                mv.visibility=View.GONE
            }
        }
    }
}