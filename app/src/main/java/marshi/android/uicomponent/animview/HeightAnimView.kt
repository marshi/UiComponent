package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import marshi.android.uicomponent.AnimationDuration

interface HeightAnimView {
  val view: View

  fun animateAbsolutely(height: Int): Animator {
    return ValueAnimator.ofInt(view.height, height).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.layoutParams.height = it.animatedValue as Int
        view.requestLayout()
      }
    }
  }
}

fun HeightAnimView.animateRelatively(
  height: Float
): Animator {
  val newHeight = (view.height + height).toInt()
  return animateAbsolutely(newHeight)
}
