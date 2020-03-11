package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import marshi.android.uicomponent.AnimationDuration

interface HeightAnimView {
  val view: View

  fun animateAbsolutely(height: Int, animatorListener: Animator.AnimatorListener) {
    val animator = ValueAnimator.ofInt(view.height, height).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.layoutParams.height = it.animatedValue as Int
        view.requestLayout()
      }
      addListener(animatorListener)
    }
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      play(animator)
    }.start()
  }
}

fun HeightAnimView.animateRelatively(
  height: Float,
  animatorListener: Animator.AnimatorListener
) {
  val newHeight = (view.height + height).toInt()
  animateAbsolutely(newHeight, animatorListener)
}
