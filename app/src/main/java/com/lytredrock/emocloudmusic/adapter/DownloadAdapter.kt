package com.lytredrock.emocloudmusic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.data.DownloadSongData

/**
 * description ： 下载记录的适配器
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 13:48
 */

class DownloadAdapter(val data: List<DownloadSongData>) :
    RecyclerView.Adapter<DownloadAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onImageviewClick(view: ImageView, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    inner class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name = root.findViewById<TextView>(R.id.tv_downloadName)
        val author = root.findViewById<TextView>(R.id.tv_downloadAuthor)

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
            name.text = itemData.name
            author.text = itemData.author
        }
    }
}