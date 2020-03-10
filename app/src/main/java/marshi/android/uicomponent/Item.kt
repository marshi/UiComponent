package marshi.android.uicomponent

class NormalItem(
    val text: String = "text",
    var isOpened: Boolean = false
)

class AnimatingState(
    var isExpandAnimating: Boolean = false,
    var isDividerAnimating: Boolean = false,
    var isElevationAnimating: Boolean = false,
    var isCollapseAnimating: Boolean = false,
    var isHideDividerAnimating: Boolean = false,
    var isDownElevationAnimating: Boolean = false
) {
    fun isEnableAnimating() = isElevationAnimating || isDividerAnimating || isExpandAnimating
    fun isDisableAnimating() = isDownElevationAnimating|| isHideDividerAnimating|| isCollapseAnimating
}
