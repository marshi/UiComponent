package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.FloatRange
import marshi.android.uicomponent.AnimationDuration
import kotlin.math.max
import kotlin.math.min

interface AlphaAnimView {
  val view: View

  fun animate(alpha: Float, animatorListener: Animator.AnimatorListener? = null) {
    val animator = ValueAnimator.ofFloat(view.alpha, alpha).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.alpha = it.animatedValue as Float
      }
      animatorListener?.let {
        addListener(it)
      }
    }
    AnimatorSet().apply {
      interpolator = LinearInterpolator()
      play(animator)
    }.start()
  }
}

fun AlphaAnimView.animateRelatively(
  @FloatRange(from = 0.0, to = 1.0) alpha: Float,
  animatorListener: Animator.AnimatorListener
) {
  val newHeight = min(max(0f, view.height + alpha), 1f)
  animate(newHeight, animatorListener)
}
