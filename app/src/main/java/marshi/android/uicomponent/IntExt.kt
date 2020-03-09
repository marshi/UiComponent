package marshi.android.uicomponent

import android.content.Context

fun Int.px(context: Context): Int {
    return (context.resources.displayMetrics.density * this).toInt()
}
