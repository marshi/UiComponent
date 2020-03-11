package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import marshi.android.uicomponent.AnimationDuration

interface ElevationAnimView {
  val view: View

  fun animateAbsolutely(
    elevation: Float,
    animatorListener: Animator.AnimatorListener
  ) {
    val animator = ValueAnimator.ofFloat(view.elevation, elevation).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.elevation = it.animatedValue as Float
      }
      addListener(animatorListener)
    }
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      play(animator)
    }.start()
  }
}

fun ElevationAnimView.animateRelatively(
  elevation: Float,
  animatorListener: Animator.AnimatorListener
) {
  val newElevation = (view.elevation + elevation)
  animateAbsolutely(newElevation, animatorListener)
}
