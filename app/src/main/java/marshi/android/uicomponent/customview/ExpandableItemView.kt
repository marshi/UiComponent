package marshi.android.uicomponent.customview

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import marshi.android.uicomponent.R
import marshi.android.uicomponent.animview.ElevationAnimView
import marshi.android.uicomponent.animview.animateRelatively
import marshi.android.uicomponent.databinding.ExpandableItemViewBinding

class ExpandableItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ElevationAnimView {
  private val expandHeight = context.resources.getDimension(R.dimen.expand_height)
  private val itemElevation = context.resources.getDimension(R.dimen.item_elevation)

  override val view
    get() = this

  private val binding by lazy { ExpandableItemViewBinding.bind(this) }

  fun expand(animatorListener: Animator.AnimatorListener? = null) {
    val divider = binding.divider
    val dividerShowAnimator = divider.animateAbsolutely(1f)
    divider.visibility = View.VISIBLE
    val expandAnimator = binding.expandConstraint.animateRelatively(expandHeight)
    val elevationUpAnimator = animateRelatively(itemElevation)
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      playTogether(dividerShowAnimator, expandAnimator, elevationUpAnimator)
      animatorListener?.let {
        addListener(it)
      }
    }.start()
  }

  fun collapse(animatorListener: Animator.AnimatorListener? = null) {
    val divider = binding.divider
    val dividerHideAnimator = divider.animateAbsolutely(0f)
    val collapseAnimator = binding.expandConstraint.animateAbsolutely(0)
    val elevationDownAnimator = animateAbsolutely(0f)
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      playTogether(dividerHideAnimator, collapseAnimator, elevationDownAnimator)
      animatorListener?.let {
        addListener(it)
      }
    }.start()
  }
}

