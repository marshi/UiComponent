package marshi.android.uicomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.normal_item.view.*
import marshi.android.uicomponent.databinding.NormalItemBinding

class Adapter(
  private val context: Context,
  private val lifecycleOwner: LifecycleOwner,
  private val list: MutableList<NormalItem>
) : RecyclerView.Adapter<VH>() {

  private val clickPositionLiveData = MutableLiveData<Int>()
  private val animatingState = AnimatingState()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val inflater = LayoutInflater.from(parent.context)
    val binding = NormalItemBinding.inflate(inflater, parent, false)
    return VH(binding)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: VH, position: Int) {
    val itemView = holder.itemView
    val item = list[position]
    if (holder.binding is NormalItemBinding) {
      holder.binding.text.text = item.text
    }
    val animationListener: (AnimationType) -> AnimationListener = { type ->
      AnimationListener(
        onStart = {
          when (type) {
            AnimationType.Expand -> animatingState.isExpandAnimating = true
            AnimationType.Collapse -> animatingState.isCollapseAnimating = true
            AnimationType.ShowDivider -> animatingState.isDividerAnimating = true
            AnimationType.HideDivider -> animatingState.isHideDividerAnimating = true
            AnimationType.UpElevation -> animatingState.isElevationAnimating = true
            AnimationType.DownElevation -> animatingState.isDownElevationAnimating = true
          }
        },
        onEnd = {
          when (type) {
            AnimationType.Expand -> animatingState.isExpandAnimating = false
            AnimationType.Collapse -> animatingState.isCollapseAnimating = false
            AnimationType.ShowDivider -> animatingState.isDividerAnimating = false
            AnimationType.HideDivider -> animatingState.isHideDividerAnimating = false
            AnimationType.UpElevation -> animatingState.isElevationAnimating = false
            AnimationType.DownElevation -> animatingState.isDownElevationAnimating = false
          }
        }
      )
    }
    val expandView = itemView.expand
    val expandAnim = ResizeAnimation(expandView, 40.px(context), 0).apply {
      setAnimationListener(animationListener(AnimationType.Expand))
    }
    val fadeInAnimation = AlphaAnimation(0.0f, 1.0f).apply {
      duration = 300
      fillAfter = true
      setAnimationListener(animationListener(AnimationType.ShowDivider))
    }
    val fadeOutAnimation = AlphaAnimation(1.0f, 0f).apply {
      duration = 300
      fillAfter = true
      setAnimationListener(animationListener(AnimationType.HideDivider))
    }
    val upElevationAnimation = ElevationAnimation(itemView, 20.px(context), 0).apply {
      setAnimationListener(animationListener(AnimationType.UpElevation))
    }
    val downElevationAnimation =
      ElevationAnimation(itemView, (-20).px(context), 20.px(context)).apply {
        setAnimationListener(animationListener(AnimationType.DownElevation))
      }
    clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
      if (!item.isOpened && position == clickPosition) {
        item.isOpened = true
        itemView.divider.startAnimation(fadeInAnimation)
        itemView.divider.visibility = View.VISIBLE
        expandView.startAnimation(expandAnim)
        itemView.startAnimation(upElevationAnimation)
      } else if (item.isOpened) {
        println("height: ${expandView.measuredHeight} ${expandAnim.hasEnded()}")
        val collapseAnim = ResizeAnimation(
          expandView,
          (-40).px(context),
          40.px(context)
        ).apply {
          setAnimationListener(animationListener(AnimationType.Collapse))
        }
        item.isOpened = false
        itemView.divider.startAnimation(fadeOutAnimation)
        expandView.startAnimation(collapseAnim)
        itemView.startAnimation(downElevationAnimation)
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

class VH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
