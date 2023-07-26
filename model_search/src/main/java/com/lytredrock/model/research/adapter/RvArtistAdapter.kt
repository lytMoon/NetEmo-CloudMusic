package com.lytredrock.model.research.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lytredrock.model.research.R
import com.lytredrock.model.research.musicdata.Artist
import com.lytredrock.model.research.musicdata.Song
import com.lytredrock.model.research.ui.ArtistShowActivity

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 13:17
 * version: 1.0
 */
class RvArtistAdapter(val data: List<Artist>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RvArtistAdapter.InnerHolder>() {

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val artistPic: ImageView = root.findViewById(R.id.iv_artist_pic_fragment)
        val artistName: TextView = root.findViewById(R.id.tv_artist_name_fragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_artist, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {

        val itemData = data[position]
        holder.apply {
            try {
                holder.artistName.text = itemData.name
                Glide.with(activity)
                    .load(itemData.img1v1Url)
                    .transform(RoundedCorners(180))
                    .into(artistPic)
            } catch (e: NullPointerException) {
                Log.d("NullPointerException", "artistAdapter");
            }

        }
        /**
         * 设置点击事件
         */
        holder.itemView.setOnClickListener {
            ARouter.getInstance()
                .build("/artist/music")
                .withString("id",itemData.id.toString())
                .withString("picUrl",itemData.img1v1Url)
                .navigation()
        }


    }
}