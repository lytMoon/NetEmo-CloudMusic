//package commentadapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.FragmentActivity
//import androidx.recyclerview.widget.RecyclerView
//import com.alibaba.android.arouter.launcher.ARouter
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//
///**
// * description ：
// * author : lytMoon
// * email : yytds@foxmail.com
// * date : 2023/7/21 20:16
// * version: 1.0
// */
//class RvMvAdapter(val data: List<Mv>, private val activity: FragmentActivity) :
//    RecyclerView.Adapter<RvMvAdapter.InnerHolder>() {
//
//    private var clickInterface: ClickInterface? = null
//
//    interface ClickInterface {
//        fun onTitleClick(view: View, position: Int)
//    }
//
//
//    fun setOnclick(clickInterface: ClickInterface) {
//        this.clickInterface = clickInterface
//    }
//
//    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
//        val mvName: TextView = root.findViewById(R.id.tv_mv_title)
//        val mvAuthorName: TextView = root.findViewById(R.id.tv_mv_author_name)
//        val mvPic: ImageView = root.findViewById(R.id.iv_mv_pic)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_mv, parent, false)
//        return InnerHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
//        val itemData = data[position]
//        holder.apply {
//            try {
//                holder.mvAuthorName.text = itemData.artistName
//                holder.mvName.text = itemData.name
//                Glide.with(activity)
//                    .load(itemData.cover)
//                    .transform(RoundedCorners(20))
//                    .into(holder.mvPic)
//
//            } catch (e: NullPointerException) {
//                Log.d("NullPointerException", " 空指针异常")
//            }
//        }
//        holder.itemView.setOnClickListener {
//            ARouter.getInstance()
//                .build("/mv/mvPlay")
//                .withString("mvUid",itemData.id.toString())
//                .withString("mvName",itemData.name)
//                .navigation()
//
//        }
//
//    }
//}