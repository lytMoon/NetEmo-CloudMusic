package com.lytredrock.model.research.adapter

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
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
class RvSongAdapter() : ListAdapter<Song, RvSongAdapter.RvNewsViewHolder>(RvNewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song,parent, false)
        return RvNewsViewHolder(view)
    }
    override fun onBindViewHolder(holder: RvNewsViewHolder, position: Int) {
        val music = getItem(position)
        holder.bind(music)
    }
     class RvNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicName: TextView = itemView.findViewById(R.id.tv_name)
        val artistName :TextView  = itemView.findViewById(R.id.tv_artist)

        fun bind(music: Song) {

            musicName.text=music.name
            Log.d("bind5555","(RvSongAdapter.kt:38)-->> $music");

            //通过使用glide来对图片进行绑定和一些处理
//            Glide.with(itemView.context)
//                .load(news.images.get(0))
//                .transform(CenterCrop(), RoundedCorners(10))//设置圆角
//                .into(imageView)
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, NewsReadingActivity::class.java)
//                //设置标志符
//                intent.putExtra("topNewsUrl", news.url)
//                intent.putExtra("newsId", news.id)
//                itemView.context.startActivity(intent)
//            }
        }
    }
     class RvNewsDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
