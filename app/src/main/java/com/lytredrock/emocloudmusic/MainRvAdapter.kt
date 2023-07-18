package com.lytredrock.emocloudmusic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lytredrock.emocloudmusic.data.Data

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/17 19:57
 */
class MainRvAdapter(val data: List<Data>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<MainRvAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onTitleClick(view: View, position: Int)
    }

     fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val title = root.findViewById<TextView>(R.id.tv_find)
        val ivfind = root.findViewById<ImageView>(R.id.iv_Find)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_main, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            title.text = itemData.name
            val temp = itemData.iconUrl
            Glide.with(activity).load(temp).into(ivfind)
        }
        holder.title.setOnClickListener {
            clickInterface?.onTitleClick(it, position)
        }
    }
}