package marshi.android.uicomponent

import android.view.animation.Animation

class AnimationListener(
    private val onStart: (animation: Animation?) -> Unit,
    private val onEnd: (animation: Animation?) -> Unit
): Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        onEnd(animation)
    }

    override fun onAnimationStart(animation: Animation?) {
        onStart(animation)
    }
}
