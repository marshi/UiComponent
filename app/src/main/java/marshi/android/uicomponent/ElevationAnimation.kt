package marshi.android.uicomponent

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ElevationAnimation(
    private val view: View,
    private val addElevation: Int,
    private val startElevation: Int
) : Animation() {

    init {
        duration = 300
    }

    override fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation?
    ) {
        val newElevation = (startElevation + addElevation * interpolatedTime)
        view.elevation = newElevation
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}
