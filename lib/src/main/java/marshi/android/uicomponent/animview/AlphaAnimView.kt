package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.FloatRange
import marshi.android.uicomponent.AnimationDuration
import kotlin.math.max
import kotlin.math.min

interface AlphaAnimView {
  val view: View

  fun absolutelyAnimator(
    alpha: Float
  ): Animator {
    return ValueAnimator.ofFloat(view.alpha, alpha).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.alpha = it.animatedValue as Float
      }
    }
  }
}

fun AlphaAnimView.relativelyAnimator(
  @FloatRange(from = 0.0, to = 1.0) alpha: Float
): Animator {
  val newHeight = min(max(0f, view.height + alpha), 1f)
  return absolutelyAnimator(newHeight)
}
