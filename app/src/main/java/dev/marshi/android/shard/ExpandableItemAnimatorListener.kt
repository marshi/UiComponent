package dev.marshi.android.shard

import android.animation.Animator

class ExpandableItemAnimatorListener : Animator.AnimatorListener {

  var isAnimating = false

  override fun onAnimationStart(animation: Animator?) {
    isAnimating = true
  }

  override fun onAnimationEnd(animation: Animator?) {
    isAnimating = false
  }

  override fun onAnimationRepeat(animation: Animator?) {}

  override fun onAnimationCancel(animation: Animator?) {}
}
