package com.sd.lib.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.ripple
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun InternalSurface(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = RectangleShape,
  color: Color = MaterialTheme.colorScheme.surface,
  contentColor: Color = contentColorFor(color),
  tonalElevation: Dp = 0.dp,
  shadowElevation: Dp = 0.dp,
  border: BorderStroke? = null,
  interactionSource: MutableInteractionSource? = null,
  content: @Composable () -> Unit,
) {
  val absoluteElevation = LocalAbsoluteTonalElevation.current + tonalElevation
  CompositionLocalProvider(
    LocalContentColor provides contentColor,
    LocalAbsoluteTonalElevation provides absoluteElevation
  ) {
    Box(
      modifier = modifier
        .surface(
          shape = shape,
          backgroundColor = surfaceColorAtElevation(color = color, elevation = absoluteElevation),
          border = border,
          shadowElevation = with(LocalDensity.current) { shadowElevation.toPx() }
        )
        .clickable(
          interactionSource = interactionSource,
          indication = ripple(),
          enabled = enabled,
          onClick = onClick
        ),
      propagateMinConstraints = true
    ) {
      content()
    }
  }
}

@Stable
private fun Modifier.surface(
  shape: Shape,
  backgroundColor: Color,
  border: BorderStroke?,
  shadowElevation: Float,
) = this
  .then(
    if (shadowElevation > 0f) {
      Modifier.graphicsLayer(
        shadowElevation = shadowElevation,
        shape = shape,
        clip = false
      )
    } else {
      Modifier
    }
  )
  .then(if (border != null) Modifier.border(border, shape) else Modifier)
  .background(color = backgroundColor, shape = shape)
  .clip(shape)

@Composable
private fun surfaceColorAtElevation(color: Color, elevation: Dp): Color =
  MaterialTheme.colorScheme.applyTonalElevation(color, elevation)

@Composable
@ReadOnlyComposable
private fun ColorScheme.applyTonalElevation(backgroundColor: Color, elevation: Dp): Color {
  val tonalElevationEnabled = LocalTonalElevationEnabled.current
  return if (backgroundColor == surface && tonalElevationEnabled) {
    surfaceColorAtElevation(elevation)
  } else {
    backgroundColor
  }
}