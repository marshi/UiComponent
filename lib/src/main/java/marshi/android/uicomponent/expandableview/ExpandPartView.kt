package marshi.android.uicomponent.expandableview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.withStyledAttributes
import marshi.android.uicomponent.R
import marshi.android.uicomponent.animview.HeightAnimView

class ExpandPartView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), HeightAnimView {
  override val view
    get() = this

  internal var expandHeight: Float = 0f

  init {
    context.withStyledAttributes(attrs, R.styleable.ExpandPartView, defStyleAttr, 0) {
      expandHeight = getDimensionOrThrow(R.styleable.ExpandPartView_expand_height)
    }
  }
}
