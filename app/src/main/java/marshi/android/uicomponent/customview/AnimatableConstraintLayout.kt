package marshi.android.uicomponent.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import marshi.android.uicomponent.animview.ElevationAnimView
import marshi.android.uicomponent.animview.HeightAnimView


class AnimatableConstraintLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
  HeightAnimView,
  ElevationAnimView {
  override val view = this
}
