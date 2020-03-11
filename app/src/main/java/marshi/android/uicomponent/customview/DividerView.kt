package marshi.android.uicomponent.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import marshi.android.uicomponent.animview.AlphaAnimView

class DividerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes),
  AlphaAnimView {
  override val view: View
    get() = this
}
