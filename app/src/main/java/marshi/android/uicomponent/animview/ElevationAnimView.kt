package marshi.android.uicomponent.animview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import marshi.android.uicomponent.AnimationDuration

interface ElevationAnimView {
  val view: View

  fun absolutelyAnimator(
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

fun ElevationAnimView.relativelyAnimator(
  elevation: Float
) : Animator{
  val newElevation = (view.elevation + elevation)
  return absolutelyAnimator(newElevation)
}
