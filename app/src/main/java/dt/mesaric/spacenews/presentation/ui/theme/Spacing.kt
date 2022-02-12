package dt.mesaric.spacenews.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val mini: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large:Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }