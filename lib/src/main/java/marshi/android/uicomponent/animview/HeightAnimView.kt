package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View

interface HeightAnimView {
  val view: View

  fun absoluteAnimator(
    height: Int,
    duration: Long
  ): Animator {
    return ValueAnimator.ofInt(view.height, height).apply {
      this.duration = duration
      addUpdateListener {
        view.layoutParams.height = it.animatedValue as Int
        view.requestLayout()
      }
    }
  }

  fun relativeAnimator(
    height: Float,
    duration: Long
  ): Animator {
    val newHeight = (view.height + height).toInt()
    return absoluteAnimator(newHeight, duration)
  }
}
