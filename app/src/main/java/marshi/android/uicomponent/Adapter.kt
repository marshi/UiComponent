package marshi.android.uicomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.normal_item.view.*
import marshi.android.uicomponent.databinding.NormalItemBinding

class Adapter(
  context: Context,
  private val lifecycleOwner: LifecycleOwner,
  private val list: MutableList<NormalItem>
) : RecyclerView.Adapter<VH>() {

  private val clickPositionLiveData = MutableLiveData<Int>()
  private val animatingState = AnimatingState()
  private val animationFactory = AnimationFactory(animatingState)
  private val expandHeight = context.resources.getDimension(R.dimen.expand_height)
  private val itemElevation = context.resources.getDimension(R.dimen.item_elevation)
  private val animationDuration = AnimationDuration.value

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val inflater = LayoutInflater.from(parent.context)
    val binding = NormalItemBinding.inflate(inflater, parent, false)
    return VH(binding)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: VH, position: Int) {
    val itemView = holder.itemView
    val item = list[position]
    val expandView = itemView.expand
    val expandAnim = animationFactory.expandAnim(expandView, expandHeight)
    val fadeInAnim = animationFactory.fadeInAnim(animationDuration)
    val fadeOutAnim = animationFactory.fadeOutAnim(animationDuration)
    val upElevationAnim =
      animationFactory.upElevationAnim(itemView, itemElevation)
    val downElevationAnim =
      animationFactory.downElevationAnim(itemView, itemElevation)
    val collapseAnim = animationFactory.collapseAnim(expandView, expandHeight)
    holder.binding.text.text = item.text

    clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
      if (!item.isOpened && position == clickPosition) {
        item.isOpened = true
        expand(itemView, expandAnim, fadeInAnim, upElevationAnim)
      } else if (item.isOpened) {
        item.isOpened = false
        collapse(itemView, collapseAnim, fadeOutAnim, downElevationAnim)
      }
    })
    itemView.setOnClickListener {
      if (animatingState.isEnableAnimating() || animatingState.isDisableAnimating()) {
        return@setOnClickListener
      }
      clickPositionLiveData.value = position
    }
  }

  private fun expand(
    itemView: View,
    expandAnim: Animation,
    fadeInAnim: Animation,
    upElevationAnim: Animation
  ) {
    itemView.divider.startAnimation(fadeInAnim)
    itemView.divider.visibility = View.VISIBLE
    itemView.expand.startAnimation(expandAnim)
    itemView.startAnimation(upElevationAnim)
  }

  private fun collapse(
    itemView: View,
    collapseAnim: Animation,
    fadeOutAnim: Animation,
    downElevationAnim: Animation
  ) {
    itemView.divider.startAnimation(fadeOutAnim)
    itemView.expand.startAnimation(collapseAnim)
    itemView.startAnimation(downElevationAnim)
  }
}

class VH(val binding: NormalItemBinding) : RecyclerView.ViewHolder(binding.root)
