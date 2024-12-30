package com.sd.lib.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/**
 * [TextButton]
 */
@Composable
fun FTextButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.textShape,
  colors: ButtonColors = ButtonDefaults.textButtonColors(
    contentColor = LocalContentColor.current,
    disabledContentColor = LocalContentColor.current.copy(0.38f),
  ),
  border: BorderStroke? = null,
  contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
  interactionSource: MutableInteractionSource? = null,
  content: @Composable RowScope.() -> Unit,
) {
  FButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )
}

/**
 * [Button]
 */
@Composable
fun FButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.shape,
  colors: ButtonColors = ButtonDefaults.buttonColors(),
  border: BorderStroke? = null,
  contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
  interactionSource: MutableInteractionSource? = null,
  content: @Composable RowScope.() -> Unit,
) {
  @Suppress("NAME_SHADOWING")
  val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
  val containerColor = colors.containerColorExt(enabled)
  val contentColor = colors.contentColorExt(enabled)
  InternalSurface(
    onClick = onClick,
    modifier = modifier.semantics { role = Role.Button },
    enabled = enabled,
    shape = shape,
    color = containerColor,
    contentColor = contentColor,
    border = border,
    interactionSource = interactionSource
  ) {
    ProvideContentColorTextStyle(
      contentColor = contentColor,
      textStyle = MaterialTheme.typography.labelLarge
    ) {
      Row(
        Modifier
          .defaultMinSize(
            minWidth = 48.dp,
            minHeight = 24.dp,
          )
          .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content
      )
    }
  }
}

@Stable
private fun ButtonColors.containerColorExt(enabled: Boolean): Color =
  if (enabled) containerColor else disabledContainerColor

@Stable
private fun ButtonColors.contentColorExt(enabled: Boolean): Color =
  if (enabled) contentColor else disabledContentColor

@Composable
private fun ProvideContentColorTextStyle(
  contentColor: Color,
  textStyle: TextStyle,
  content: @Composable () -> Unit,
) {
  val mergedStyle = LocalTextStyle.current.merge(textStyle)
  CompositionLocalProvider(
    LocalContentColor provides contentColor,
    LocalTextStyle provides mergedStyle,
    content = content
  )
}