package marshi.android.uicomponent

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ResizeAnimation(
    private val view: View,
    private val addHeight: Int,
    private val startHeight: Int
) : Animation() {

    init {
        duration = 300
    }

    override fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation?
    ) {
        val newHeight = (startHeight + addHeight * interpolatedTime)
        view.layoutParams.height = newHeight.toInt()
        view.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}