package marshi.android.uicomponent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import marshi.android.uicomponent.databinding.ExpandItemBinding
import marshi.android.uicomponent.databinding.NormalItemBinding
import java.lang.IllegalArgumentException

class Adapter(val list: MutableList<Item>) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when(viewType) {
            0 -> NormalItemBinding.inflate(inflater, parent, false)
            1 -> ExpandItemBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException()
        }
        return VH(binding)
    }

    private fun add(item: Item) {
        list.add(item)
        notifyItemInserted(list.size - 1)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val itemView = holder.itemView
        val item = list[position]
        itemView.setOnClickListener {
            if (itemView.elevation == 0f) {
                itemView.elevation = 20f
            } else {
                itemView.elevation = 0f
            }
            (item as? NormalItem)?.isOpened = true
            add(ExpandItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is NormalItem -> 0
            is ExpandItem -> 1
        }
    }
}

class VH(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {

}
