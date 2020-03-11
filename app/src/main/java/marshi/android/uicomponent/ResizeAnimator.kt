package marshi.android.uicomponent

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

interface HeightAnimView {
  val view: View

  fun animate(height: Int) {
    val animator = ValueAnimator.ofInt(view.height, height).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.layoutParams.height = it.animatedValue as Int
        view.requestLayout()
      }
    }
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      play(animator)
    }.start()
  }
}

fun HeightAnimView.animateRelatively(
  height: Float
) {
  val newHeight = (view.height + height).toInt()
  animate(newHeight)
}

fun HeightAnimView.animateAbsolutely(
  height: Float
) {
  val newHeight = height.toInt()
  animate(newHeight)
}
