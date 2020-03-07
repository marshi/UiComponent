package marshi.android.uicomponent

sealed class Item

object ExpandItem : Item()

class NormalItem(val text: String = "text", var isOpened: Boolean = false) : Item() {
}
