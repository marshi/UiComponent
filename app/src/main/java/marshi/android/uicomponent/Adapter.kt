package marshi.android.uicomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import marshi.android.uicomponent.customview.ExpandableItemView

class Adapter(
  context: Context,
  private val lifecycleOwner: LifecycleOwner,
  private val list: MutableList<NormalItem>
) : RecyclerView.Adapter<VH>() {

  private val clickPositionLiveData = MutableLiveData<Int>()
  private val animatingState = AnimatingState()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//    val inflater = LayoutInflater.from(parent.context)
//    val binding = ExpandableItemViewBinding.inflate(inflater, parent, false)
    val itemView =
      LayoutInflater.from(parent.context)
        .inflate(R.layout.expandable_item_view, parent, false) as ExpandableItemView
    return VH(itemView)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: VH, position: Int) {
    val itemView = holder.view
    val item = list[position]
//    holder.binding.text.text = item.text

    clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
      if (!item.isOpened && position == clickPosition) {
        item.isOpened = true
        itemView.expand()
      } else if (item.isOpened) {
        item.isOpened = false
        itemView.collapse()
      }
    })
    itemView.setOnClickListener {
      if (animatingState.isEnableAnimating() || animatingState.isDisableAnimating()) {
        return@setOnClickListener
      }
      clickPositionLiveData.value = position
    }
  }
}

class VH(val view: ExpandableItemView) : RecyclerView.ViewHolder(view)
