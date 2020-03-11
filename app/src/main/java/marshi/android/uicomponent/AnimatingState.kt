package marshi.android.uicomponent

class AnimatingState(
  var isExpandAnimating: Boolean = false,
  var isDividerAnimating: Boolean = false,
  var isElevationAnimating: Boolean = false,
  var isCollapseAnimating: Boolean = false,
  var isHideDividerAnimating: Boolean = false,
  var isDownElevationAnimating: Boolean = false
) {
  fun isEnableAnimating() = isElevationAnimating || isDividerAnimating || isExpandAnimating
  fun isDisableAnimating() =
    isDownElevationAnimating || isHideDividerAnimating || isCollapseAnimating

  fun onStart(type: AnimationType) = when (type) {
    AnimationType.Expand -> isExpandAnimating = true
    AnimationType.Collapse -> isCollapseAnimating = true
    AnimationType.ShowDivider -> isDividerAnimating = true
    AnimationType.HideDivider -> isHideDividerAnimating = true
    AnimationType.UpElevation -> isElevationAnimating = true
    AnimationType.DownElevation -> isDownElevationAnimating = true
  }

  fun onEnd(type: AnimationType) = when (type) {
    AnimationType.Expand -> isExpandAnimating = false
    AnimationType.Collapse -> isCollapseAnimating = false
    AnimationType.ShowDivider -> isDividerAnimating = false
    AnimationType.HideDivider -> isHideDividerAnimating = false
    AnimationType.UpElevation -> isElevationAnimating = false
    AnimationType.DownElevation -> isDownElevationAnimating = false
  }
}
