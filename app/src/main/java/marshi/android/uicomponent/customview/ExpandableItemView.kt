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

//  private val binding = ExpandableItemViewBinding.bind(this)

  fun expand() {
    val divider = findViewById<DividerView>(R.id.divider)
    val expandView = findViewById<ExpandPartConstraintLayout>(R.id.expand_constraint)
    divider.animate(
      1f,
      animationFactory.animatorListener(AnimationType.ShowDivider)
    )
    divider.visibility = View.VISIBLE
    expandView.animateRelatively(
      expandHeight,
      animationFactory.animatorListener(AnimationType.Expand)
    )
//    binding.expandConstraint
    animateRelatively(
      itemElevation,
      animationFactory.animatorListener(AnimationType.UpElevation)
    )
  }

  fun collapse() {
    val divider = findViewById<DividerView>(R.id.divider)
    val expandView = findViewById<ExpandPartConstraintLayout>(R.id.expand_constraint)
    divider.animate(
      0f,
      animationFactory.animatorListener(AnimationType.HideDivider)
    )
    expandView.animateAbsolutely(
      0,
      animationFactory.animatorListener(AnimationType.Collapse)
    )
    animateAbsolutely(
      0f,
      animationFactory.animatorListener(AnimationType.DownElevation)
    )
  }
}

