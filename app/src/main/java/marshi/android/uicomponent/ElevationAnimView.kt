package marshi.android.uicomponent

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

interface ElevationAnimView {
  val view: View

  fun animateAbsolutely(elevation: Float) {
    val animator = ValueAnimator.ofFloat(view.elevation, elevation).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.elevation = it.animatedValue as Float
      }
    }
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      play(animator)
    }.start()
  }
}

fun ElevationAnimView.animateRelatively(elevation: Float) {
  val newElevation = (view.elevation + elevation)
  animateAbsolutely(newElevation)
}
