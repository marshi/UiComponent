package marshi.android.uicomponent

import android.view.animation.AlphaAnimation

class AnimationFactory(private val animatingState: AnimatingState) {

  private val animationListener: (AnimationType) -> AnimationListener = { type ->
    AnimationListener(
      onStart = {
        animatingState.onStart(type)
      },
      onEnd = {
        animatingState.onEnd(type)
      }
    )
  }

//  fun expandAnim(
//    expandView: View,
//    expandHeight: Float
//  ) = ResizeAnimation(expandView, expandHeight, 0f).apply {
//    setAnimationListener(animationListener(AnimationType.Expand))
//  }
//
//  fun collapseAnim(
//    expandView: View,
//    expandHeight: Float
//  ) = ResizeAnimation(
//    expandView,
//    -expandHeight,
//    expandHeight
//  ).apply {
//    setAnimationListener(animationListener(AnimationType.Collapse))
//  }

  fun fadeInAnim(
    animationDuration: Long
  ) = AlphaAnimation(0.0f, 1.0f).apply {
    duration = animationDuration
    fillAfter = true
    setAnimationListener(animationListener(AnimationType.ShowDivider))
  }

  fun fadeOutAnim(
    animationDuration: Long
  ) = AlphaAnimation(1.0f, 0f).apply {
    duration = animationDuration
    fillAfter = true
    setAnimationListener(animationListener(AnimationType.HideDivider))
  }

//  fun upElevationAnim(
//    itemView: View,
//    elevation: Float
//  ) = ElevationAnimation(itemView, elevation, 0f).apply {
//    setAnimationListener(animationListener(AnimationType.UpElevation))
//  }

//  fun downElevationAnim(
//    itemView: View,
//    elevation: Float
//  ) =
//    ElevationAnimation(itemView, -elevation, elevation).apply {
//      setAnimationListener(animationListener(AnimationType.DownElevation))
//    }
}
