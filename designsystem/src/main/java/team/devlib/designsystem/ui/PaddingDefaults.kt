package team.devlib.designsystem.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object PaddingDefaults {

    val None = 0.dp
    val ExtraSmall = 4.dp
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val ExtraLarge = 20.dp

    val Horizontal = PaddingValues(
        start = Large,
        top = None,
        end = Large,
        bottom = None,
    )

    val Vertical = PaddingValues(
        start = None,
        top = Medium,
        end = None,
        bottom = Medium,
    )

    fun screen(
        start: Dp = Large,
        top: Dp = Medium,
        end: Dp = Large,
        bottom: Dp = Medium,
    ): PaddingValues = PaddingValues(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
    )

    fun screen(
        horizontal: Dp = Large,
        vertical: Dp = Medium,
    ): PaddingValues = screen(
        start = horizontal,
        top = vertical,
        end = horizontal,
        bottom = horizontal,
    )
}

fun Modifier.screenPadding(
    start: Dp = PaddingDefaults.Large,
    top: Dp = PaddingDefaults.Medium,
    end: Dp = PaddingDefaults.Large,
    bottom: Dp = PaddingDefaults.Medium,
): Modifier = this.padding(
    start = start,
    top = top,
    end = end,
    bottom = bottom,
)

fun Modifier.screenPadding(
    horizontal: Dp = PaddingDefaults.Large,
    vertical: Dp = PaddingDefaults.Medium,
): Modifier = this.padding(
    horizontal = horizontal,
    vertical = vertical,
)

fun Modifier.horizontalPadding(
    value: Dp = PaddingDefaults.Large,
): Modifier = this.padding(horizontal = value)

fun Modifier.verticalPadding(
    value: Dp = PaddingDefaults.Small,
): Modifier = this.padding(vertical = value)

fun Modifier.startPadding(
    value: Dp = PaddingDefaults.Large,
): Modifier = this.padding(start = value)

fun Modifier.topPadding(
    value: Dp = PaddingDefaults.Medium,
): Modifier = this.padding(top = value)

fun Modifier.endPadding(
    value: Dp = PaddingDefaults.Large,
): Modifier = this.padding(end = value)

fun Modifier.bottomPadding(
    value: Dp = PaddingDefaults.Medium,
): Modifier = this.padding(bottom = value)

val DefaultVerticalSpace = PaddingDefaults.Small

val LargeVerticalSpace = PaddingDefaults.Medium

val DefaultHorizontalSpace = PaddingDefaults.Small

val LargeHorizontalSpace = PaddingDefaults.Medium
