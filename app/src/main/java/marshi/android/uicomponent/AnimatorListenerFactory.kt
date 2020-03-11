package marshi.android.uicomponent

import android.animation.Animator

class AnimatorListenerFactory(private val animatingState: AnimatingState) {

  fun animatorListener(type: AnimationType): Animator.AnimatorListener {
    return object : Animator.AnimatorListener{
      override fun onAnimationStart(animation: Animator?) {
        animatingState.onStart(type)
      }
      override fun onAnimationEnd(animation: Animator?) {
        animatingState.onEnd(type)
      }
      override fun onAnimationRepeat(animation: Animator?) {
      }
      override fun onAnimationCancel(animation: Animator?) {
      }
    }
  }
}
