package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View

interface ElevationAnimView {
  val view: View

  fun absoluteAnimator(
    elevation: Float,
    duration: Long
  ) : Animator{
    return ValueAnimator.ofFloat(view.elevation, elevation).apply {
      this.duration = duration
      addUpdateListener {
        view.elevation = it.animatedValue as Float
      }
    }
  }
}

fun ElevationAnimView.relativeAnimator(
  elevation: Float,
  duration: Long
) : Animator{
  val newElevation = (view.elevation + elevation)
  return absoluteAnimator(newElevation, duration)
}
