package marshi.android.uicomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.normal_item.view.*
import marshi.android.uicomponent.databinding.ExpandItemBinding
import marshi.android.uicomponent.databinding.NormalItemBinding
import java.lang.IllegalArgumentException

class Adapter(val context: Context, val list: MutableList<Item>) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            0 -> NormalItemBinding.inflate(inflater, parent, false)
            1 -> ExpandItemBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException()
        }
        return VH(binding)
    }

    private fun add(item: Item, position: Int) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    private fun remove(item: Item, position: Int) {
        list.remove(item)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val itemView = holder.itemView
        val item = list[position]
        if (item !is NormalItem) return
        if (holder.binding is NormalItemBinding) {
            holder.binding.text.text = item.text
        }
        val expandView = itemView.expand
        val originalHeight = expandView.height
        val expandAnim = ResizeAnimation(expandView, 40.px(context), 0)
        itemView.setOnClickListener {
            val itemPosition = list.indexOf(item)
            if (itemView.elevation == 0f) {
                itemView.elevation = 20f
            } else {
                itemView.elevation = 0f
            }
            val normalItem = item as? NormalItem
            if (normalItem?.isOpened == true) {
                normalItem.isOpened = false
//                val expandItem = list[itemPosition + 1] as ExpandItem
//                remove(expandItem, itemPosition + 1)
                val collapseAnim = ResizeAnimation(expandView, (-40).px(context), expandView.measuredHeight)
                expandView.startAnimation(collapseAnim)
            } else {
                normalItem?.isOpened = true
                expandView.startAnimation(expandAnim)
//                add(ExpandItem(itemPosition + 1), itemPosition + 1)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is NormalItem -> 0
            is ExpandItem -> 1
        }
    }
}

class VH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

}
