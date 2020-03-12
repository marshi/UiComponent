package marshi.android.uicomponent.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import marshi.android.uicomponent.AnimatingState
import marshi.android.uicomponent.AnimationType
import marshi.android.uicomponent.AnimatorListenerFactory
import marshi.android.uicomponent.R
import marshi.android.uicomponent.animview.ElevationAnimView
import marshi.android.uicomponent.animview.animateRelatively
import marshi.android.uicomponent.databinding.ExpandableItemViewBinding

class ExpandableItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ElevationAnimView {
  private val animatingState = AnimatingState()
  private val animationFactory = AnimatorListenerFactory(animatingState)
  private val expandHeight = context.resources.getDimension(R.dimen.expand_height)
  private val itemElevation = context.resources.getDimension(R.dimen.item_elevation)

  override val view
    get() = this

  private val binding by lazy { ExpandableItemViewBinding.bind(this) }

  fun expand() {
    val divider = binding.divider
    divider.animate(
      1f,
      animationFactory.animatorListener(AnimationType.ShowDivider)
    )
    divider.visibility = View.VISIBLE
    binding.expandConstraint.animateRelatively(
      expandHeight,
      animationFactory.animatorListener(AnimationType.Expand)
    )
    val expand = binding.expandConstraint
    animateRelatively(
      itemElevation,
      animationFactory.animatorListener(AnimationType.UpElevation)
    )
  }

  fun collapse() {
    val divider = binding.divider
    divider.animate(
      0f,
      animationFactory.animatorListener(AnimationType.HideDivider)
    )
    binding.expandConstraint.animateAbsolutely(
      0,
      animationFactory.animatorListener(AnimationType.Collapse)
    )
    animateAbsolutely(
      0f,
      animationFactory.animatorListener(AnimationType.DownElevation)
    )
  }
}

