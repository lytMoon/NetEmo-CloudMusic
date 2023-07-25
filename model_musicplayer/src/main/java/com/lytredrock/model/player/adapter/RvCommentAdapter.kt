
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lytredrock.model.player.R
import com.lytredrock.model.player.playerData.HotComment

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/21 20:16
 * version: 1.0
 */
class RvCommentAdapter(val data: List<HotComment>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RvCommentAdapter.InnerHolder>() {

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val mvName: TextView = root.findViewById(R.id.tv_artist_name_mv)
        val mvComment: TextView = root.findViewById(R.id.tv_artist_comment_mv)
        val mvPic: ImageView = root.findViewById(R.id.iv_mvArtist_pic_)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rv_comments, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemData = data[position]
        holder.apply {
            try {
                holder.mvComment.text = itemData.content
                holder.mvName.text = itemData.user.nickname
                Glide.with(activity)
                    .load(itemData.user.avatarUrl)
                    .transform(RoundedCorners(180))
                    .into(holder.mvPic)

            } catch (e: NullPointerException) {
                Log.d("NullPointerException", " 空指针异常")
            }
        }


    }
}