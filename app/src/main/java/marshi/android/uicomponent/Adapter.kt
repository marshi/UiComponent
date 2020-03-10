package marshi.android.uicomponent

import android.animation.AnimatorSet
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
    private val list: MutableList<Item>
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
        if (item !is NormalItem) return
        if (holder.binding is NormalItemBinding) {
            holder.binding.text.text = item.text
        }
        val animationListener: (AnimationType) -> AnimationListener = { type ->
            AnimationListener(
                onStart = {
                    when (type) {
//                        AnimationType.Expand -> animatingState.isExpandAnimating = true
//                        AnimationType.Dividier -> animatingState.isDividerAnimating = true
//                        AnimationType.Elevation -> animatingState.isElevationAnimating = true
                    }
                },
                onEnd = {
                    when (type) {
                        AnimationType.Expand -> animatingState.isExpandAnimating = false
                        AnimationType.Dividier -> animatingState.isDividerAnimating = false
                        AnimationType.Elevation -> animatingState.isElevationAnimating = false
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
            setAnimationListener(animationListener(AnimationType.Dividier))
        }
        val fadeOutAnimation = AlphaAnimation(1.0f, 0f).apply {
            duration = 300
            fillAfter = true
            setAnimationListener(animationListener(AnimationType.Dividier))
        }
        val upElevationAnimation = ElevationAnimation(itemView, 20.px(context), 0).apply {
            setAnimationListener(animationListener(AnimationType.Elevation))
        }
        val downElevationAnimation = ElevationAnimation(itemView, (-20).px(context), 20.px(context)).apply {
            setAnimationListener(animationListener(AnimationType.Elevation))
        }

        clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
            if (!item.isOpened && position == clickPosition) {
                item.isOpened = true
                itemView.divider.startAnimation(fadeInAnimation)
                itemView.divider.visibility = View.VISIBLE
                expandView.startAnimation(expandAnim)
                itemView.startAnimation(upElevationAnimation)
            } else if (item.isOpened) {
                val collapseAnim = ResizeAnimation(
                    expandView,
                    (-40).px(context),
                    expandView.measuredHeight
                ).apply {
                    setAnimationListener(animationListener(AnimationType.Expand))
                }
                item.isOpened = false
                val animatorSet = AnimatorSet()
                itemView.divider.startAnimation(fadeOutAnimation)
                expandView.startAnimation(collapseAnim)
                itemView.startAnimation(downElevationAnimation)
            }
        })
        itemView.setOnClickListener {
            if (animatingState.isAnimating()) {
                return@setOnClickListener
            }
            animatingState.isExpandAnimating = true
            animatingState.isDividerAnimating = true
            animatingState.isElevationAnimating = true
            clickPositionLiveData.value = position
        }
    }
}

class VH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
