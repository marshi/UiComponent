package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import marshi.android.uicomponent.AnimationDuration

interface ElevationAnimView {
  val view: View

  fun absoluteAnimator(
    elevation: Float
  ) : Animator{
    return ValueAnimator.ofFloat(view.elevation, elevation).apply {
      duration = AnimationDuration.value
      addUpdateListener {
        view.elevation = it.animatedValue as Float
      }
    }
  }
}

fun ElevationAnimView.relativeAnimator(
  elevation: Float
) : Animator{
  val newElevation = (view.elevation + elevation)
  return absoluteAnimator(newElevation)
}
