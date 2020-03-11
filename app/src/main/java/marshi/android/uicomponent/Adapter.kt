package marshi.android.uicomponent

import android.animation.ValueAnimator
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
    val itemView = holder.binding
    val item = list[position]
    val expandView = itemView.expandConstraint
    val fadeInAnim = animationFactory.fadeInAnim(animationDuration)
    val fadeOutAnim = animationFactory.fadeOutAnim(animationDuration)
    val upElevationAnim = animationFactory.upElevationAnim(itemView.root, itemElevation)
    val downElevationAnim = animationFactory.downElevationAnim(itemView.root, itemElevation)
    holder.binding.text.text = item.text

    val anim = ValueAnimator.ofInt(expandView.height, 100).apply {
      addUpdateListener {

      }
    }

    clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
      if (!item.isOpened && position == clickPosition) {
        item.isOpened = true
        expand(itemView.root, itemView.expandConstraint, fadeInAnim, upElevationAnim)
      } else if (item.isOpened) {
        item.isOpened = false
        collapse(itemView.root, itemView.expandConstraint, fadeOutAnim, downElevationAnim)
      }
    })
    itemView.root.setOnClickListener {
      if (animatingState.isEnableAnimating() || animatingState.isDisableAnimating()) {
        return@setOnClickListener
      }
      clickPositionLiveData.value = position
    }
  }

  private fun expand(
    itemView: View,
    animatableConstraintLayout: HeightAnimView,
    fadeInAnim: Animation,
    upElevationAnim: Animation
  ) {
    itemView.divider.startAnimation(fadeInAnim)
    itemView.divider.visibility = View.VISIBLE
//    itemView.expand.startAnimation(expandAnim)
    animatableConstraintLayout.animateRelatively(expandHeight)
    itemView.startAnimation(upElevationAnim)
  }

  private fun collapse(
    itemView: View,
    animatableConstraintLayout: HeightAnimView,
    fadeOutAnim: Animation,
    downElevationAnim: Animation
  ) {
    itemView.divider.startAnimation(fadeOutAnim)
    animatableConstraintLayout.animateAbsolutely(0f)
//    itemView.expand.startAnimation(collapseAnim)
    itemView.startAnimation(downElevationAnim)
  }
}

class VH(val binding: NormalItemBinding) : RecyclerView.ViewHolder(binding.root)
