package marshi.android.uicomponent

sealed class Item

class ExpandItem(id: Int) : Item()

class NormalItem(val text: String = "text", var isOpened: Boolean = false, var isAnimating: Boolean = false) : Item() {
}
