package com.sd.demo.compose.button

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.demo.compose.button.theme.AppTheme
import com.sd.lib.compose.button.FButton
import com.sd.lib.compose.button.FTextButton

class SampleButton : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        Surface {
          ContentView()
        }
      }
    }
  }
}

@Composable
private fun ContentView(
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    FButton(onClick = { /*TODO*/ }) {
      Text(text = "1")
    }

    FTextButton(onClick = { /*TODO*/ }) {
      Text(text = "1")
    }

    FTextButton(
      onClick = { /*TODO*/ },
      border = BorderStroke(
        width = 1.dp,
        color = LocalContentColor.current,
      )
    ) {
      Text(text = "1")
    }
  }
}