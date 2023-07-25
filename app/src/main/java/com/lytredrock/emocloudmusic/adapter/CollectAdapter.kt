package com.lytredrock.emocloudmusic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.emocloudmusic.R
import com.lytredrock.emocloudmusic.data.Collect
import com.lytredrock.emocloudmusic.helper.CollectDataHelper

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/25 16:48
 */
class CollectAdapter(
    val data: List<Collect>,
    val helper: CollectDataHelper,
    private val activity: com.lytredrock.emocloudmusic.Collect
) :
    RecyclerView.Adapter<CollectAdapter.InnerHolder>() {

    private var clickInterface: ClickInterface? = null

    interface ClickInterface {
        fun onImageviewClick(view: ImageView, position: Int)
    }

    fun setOnclick(clickInterface: ClickInterface) {
        this.clickInterface = clickInterface
    }

    inner class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name = root.findViewById<TextView>(R.id.tv_collectName)
        val author = root.findViewById<TextView>(R.id.tv_collectAuthor)
        val more = root.findViewById<ImageView>(R.id.iv_collectMore)

        init {
            more.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, more)
                popupMenu.menuInflater.inflate(R.menu.menu_collect, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.collect_remove -> {
                            val removeSong = Collect(
                                data[absoluteAdapterPosition].name,
                                data[absoluteAdapterPosition].author,
                                data[absoluteAdapterPosition].id
                            )
                            helper.removeCollect(removeSong)
                            Toast.makeText(activity, "删除成功，退出再看看呢？", Toast.LENGTH_SHORT)
                                .show()
                            true
                        }

                        R.id.collect_download -> {

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
        val view = inflater.inflate(R.layout.item_collect, parent, false)
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