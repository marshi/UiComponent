package marshi.android.uicomponent

import android.content.Context
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.TransitionDrawable
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
    val context: Context,
    val lifecycleOwner: LifecycleOwner,
    val list: MutableList<Item>
) : RecyclerView.Adapter<VH>() {

    private val clickPositionLiveData = MutableLiveData<Int>()
    private var isAnimating = false

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
        val animationListener = AnimationListener(
            onStart = { isAnimating = true },
            onEnd = { isAnimating = false }
        )
        val expandView = itemView.expand
        val expandAnim = ResizeAnimation(expandView, 40.px(context), 0).apply {
            setAnimationListener(animationListener)
        }
        val fadeInAnimation = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 300
            fillAfter = true
        }
        val fadeOutAnimation = AlphaAnimation(1.0f, 0f).apply {
            duration = 300
            fillAfter = true
        }
        val upElevationAnimation = ElevationAnimation(itemView, 20.px(context), 0)
        val downElevationAnimation = ElevationAnimation(itemView, (-20).px(context), 20.px(context))

        clickPositionLiveData.observe(lifecycleOwner, Observer { clickPosition ->
            if (!item.isOpened && position == clickPosition) {
                val transition = itemView.background as RippleDrawable
                val transitionDrawable = transition.getDrawable(0) as TransitionDrawable
                transitionDrawable.startTransition(300)
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
                    setAnimationListener(animationListener)
                }
                item.isOpened = false
                itemView.divider.startAnimation(fadeOutAnimation)
                expandView.startAnimation(collapseAnim)
                itemView.startAnimation(downElevationAnimation)
            }
        })
        itemView.setOnClickListener {
            if (isAnimating) {
                return@setOnClickListener
            }
            clickPositionLiveData.value = position
        }
    }
}

class VH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
