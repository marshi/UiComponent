package marshi.android.uicomponent

sealed class Item

class ExpandItem(id: Int) : Item()

class NormalItem(
    val text: String = "text",
    var isOpened: Boolean = false
) : Item()

class AnimatingState(
    var isExpandAnimating: Boolean = false,
    var isDividerAnimating: Boolean = false,
    var isElevationAnimating: Boolean = false
) {
    fun isAnimating() = isElevationAnimating || isDividerAnimating || isExpandAnimating
}
