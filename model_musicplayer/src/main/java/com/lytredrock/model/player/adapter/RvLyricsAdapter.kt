

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.lytredrock.model.player.R

/**
 * description ：
 * author : lytMoon
 * email : yytds@foxmail.com
 * date : 2023/7/19 13:17
 * version: 1.0
 */
class RvLyricsAdapter(val data: List<String>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<RvLyricsAdapter.InnerHolder>() {

    class InnerHolder(root: View) : RecyclerView.ViewHolder(root) {
        val line: TextView = root.findViewById(R.id.lyrics_line)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_lyrics, parent, false)
        return InnerHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {

        val itemData = data[position]
        holder.apply {
            try {
                holder.line.text = itemData
            } catch (e: NullPointerException) {
                Log.d("NullPointerException", " ");
            }

        }


    }
}