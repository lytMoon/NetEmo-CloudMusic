package com.lytredrock.model.research.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lytredrock.model.research.R
import com.lytredrock.model.research.musicdata.Artist
import com.lytredrock.model.research.musicdata.Song

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 13:17
 * version: 1.0
 */
class RvArtistAdapter(val data: List<Artist>, private val activity: FragmentActivity):
    RecyclerView.Adapter<RvArtistAdapter.InnerHolder>()  {
    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }


    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val artistPic :ImageView=root.findViewById(R.id.iv_artist_pic_fragment)
        val artistName: TextView = root.findViewById<TextView>(R.id.tv_artist_name_fragment)
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
            holder.artistName.text=itemData.name
            Glide.with(activity)
                .load(itemData.img1v1Url)
                .transform(RoundedCorners(180))
                .into(artistPic)


//            val temp = itemData.
//            Glide.with(activity).load(temp).into(ivfind)
        }

        //这里可以进行监听事件
//        holder.artistName.setOnClickListener {
//            clickInterface?.onTitleClick(it, position)
//        }
    }



}