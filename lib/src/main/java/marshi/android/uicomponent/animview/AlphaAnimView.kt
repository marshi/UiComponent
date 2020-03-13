package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.FloatRange
import kotlin.math.max
import kotlin.math.min

interface AlphaAnimView {
  val view: View

  fun absoluteAnimator(
    alpha: Float,
    duration: Long
  ): Animator {
    return ValueAnimator.ofFloat(view.alpha, alpha).apply {
      this.duration = duration
      addUpdateListener {
        view.alpha = it.animatedValue as Float
      }
    }
  }
}

fun AlphaAnimView.relativeAnimator(
  @FloatRange(from = 0.0, to = 1.0) alpha: Float,
  duration: Long
): Animator {
  val newHeight = min(max(0f, view.height + alpha), 1f)
  return absoluteAnimator(newHeight, duration)
}
