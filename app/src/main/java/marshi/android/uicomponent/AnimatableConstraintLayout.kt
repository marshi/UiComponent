package marshi.android.uicomponent

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout


class AnimatableConstraintLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), HeightAnimView, ElevationAnimView {
  override val view = this
}
