package marshi.android.uicomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.normal_item.view.*
import marshi.android.uicomponent.animview.AlphaAnimView
import marshi.android.uicomponent.animview.ElevationAnimView
import marshi.android.uicomponent.animview.HeightAnimView
import marshi.android.uicomponent.animview.animateRelatively
import marshi.android.uicomponent.customview.DividerView
import marshi.android.uicomponent.databinding.NormalItemBinding

class Adapter(
  context: Context,
  private val lifecycleOwner: LifecycleOwner,
  private val list: MutableList<NormalItem>
) : RecyclerView.Adapter<VH>() {

  private val clickPositionLiveData = MutableLiveData<Int>()
  private val animatingState = AnimatingState()
  private val animationFactory = AnimatorListenerFactory(animatingState)
  private val expandHeight = context.resources.getDimension(R.dimen.expand_height)
  private val itemElevation = context.resources.getDimension(R.dimen.item_elevation)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val inflater = LayoutInflater.from(parent.context)
    val binding = NormalItemBinding.inflate(inflater, parent, false)
    return VH(binding)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: VH, position: Int) {
    val itemView = holder.binding
    val item = list[position]
    holder.binding.text.text = item.text

    clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
      if (!item.isOpened && position == clickPosition) {
        item.isOpened = true
        expand(
          itemView.divider,
          itemView.expandConstraint,
          itemView.rootView
        )
      } else if (item.isOpened) {
        item.isOpened = false
        collapse(
          itemView.divider,
          itemView.expandConstraint,
          itemView.rootView
        )
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
    dividerView: DividerView,
    heightAnimView: HeightAnimView,
    elevationAnimView: ElevationAnimView
  ) {
    dividerView.animate(
      1f,
      animationFactory.animatorListener(AnimationType.ShowDivider)
    )
    dividerView.divider.visibility = View.VISIBLE
    heightAnimView.animateRelatively(
      expandHeight,
      animationFactory.animatorListener(AnimationType.Expand)
    )
    elevationAnimView.animateAbsolutely(
      itemElevation,
      animationFactory.animatorListener(AnimationType.UpElevation)
    )
  }

  private fun collapse(
    itemView: AlphaAnimView,
    animatableConstraintLayout: HeightAnimView,
    elevationConstraintLayout: ElevationAnimView
  ) {
    itemView.animate(
      0f,
      animationFactory.animatorListener(AnimationType.HideDivider)
    )
    animatableConstraintLayout.animateAbsolutely(
      0,
      animationFactory.animatorListener(AnimationType.Collapse)
    )
    elevationConstraintLayout.animateAbsolutely(
      0f,
      animationFactory.animatorListener(AnimationType.DownElevation)
    )
  }
}

class VH(val binding: NormalItemBinding) : RecyclerView.ViewHolder(binding.root)
