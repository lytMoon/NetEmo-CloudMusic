package com.lytredrock.model.research.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lytredrock.model.research.R
import com.lytredrock.model.research.musicdata.Mv

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/20 16:00
 * version: 1.0
 */
class RvMvAdapter(val data: List<Mv>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RvMvAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }


    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val mvName: TextView = root.findViewById(R.id.tv_mv_title)
        val mvAuthorName: TextView = root.findViewById(R.id.tv_mv_author_name)
        val mvPic: ImageView = root.findViewById(R.id.iv_mv_pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mv, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {

            holder.mvAuthorName.text = itemData.artistName
            holder.mvName.text = itemData.name
            Glide.with(activity)
                .load(itemData.cover)
                .transform(RoundedCorners(20))
                .into(holder.mvPic)

//            val temp = itemData.
//            Glide.with(activity).load(temp).into(ivfind)
        }
//        holder.artistName.setOnClickListener {
//            clickInterface?.onTitleClick(it, position)
//        }
    }
}