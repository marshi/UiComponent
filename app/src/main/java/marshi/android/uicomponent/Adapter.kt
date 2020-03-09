package marshi.android.uicomponent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationSet
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.normal_item.view.*
import marshi.android.uicomponent.databinding.NormalItemBinding
import java.lang.IllegalArgumentException

class Adapter(val context: Context, val list: MutableList<Item>) : RecyclerView.Adapter<VH>() {

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
            onStart = { item.isAnimating = true },
            onEnd = { item.isAnimating = false }
        )
        val expandView = itemView.expand
        val originalHeight = expandView.height
        val expandAnim = ResizeAnimation(expandView, 40.px(context), 0).apply {
            setAnimationListener(animationListener)
        }
        val upElevationAnimation = ElevationAnimation(itemView, 20.px(context), 0).apply {
            setAnimationListener(animationListener)
        }
        val downElevationAnimation =
            ElevationAnimation(itemView, (-20).px(context), 20.px(context)).apply {
                setAnimationListener(animationListener)
            }
        itemView.setOnClickListener {
            if (item.isAnimating) {
                return@setOnClickListener
            }
            val normalItem = item as? NormalItem
            if (normalItem?.isOpened == true) {
                normalItem.isOpened = false
                val collapseAnim = ResizeAnimation(
                    expandView,
                    (-40).px(context),
                    expandView.measuredHeight
                ).apply {
                    setAnimationListener(animationListener)
                }
                expandView.startAnimation(collapseAnim)
                itemView.startAnimation(downElevationAnimation)
            } else {
                normalItem?.isOpened = true
                expandView.startAnimation(expandAnim)
                itemView.startAnimation(upElevationAnimation)
            }
        }
    }
}

class VH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

}
