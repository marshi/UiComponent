package marshi.android.uicomponent

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ResizeAnimation(
  private val view: View,
  private val addHeight: Float,
  private val startHeight: Float
) : Animation() {

  init {
    duration = AnimationDuration.value
  }

  override fun applyTransformation(
    interpolatedTime: Float,
    t: Transformation?
  ) {
    val newHeight = (startHeight + addHeight * interpolatedTime)
    view.layoutParams.height = newHeight.toInt()
    view.requestLayout()
  }

  override fun willChangeBounds(): Boolean {
    return true
  }
}
