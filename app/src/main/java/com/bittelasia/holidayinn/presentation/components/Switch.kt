package com.bittelasia.holidayinn.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Switch
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MySwitch(onSwitchStateChanged: (Boolean) -> Unit) {
    var switchState by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Ping Server",
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                color = Color.White,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = switchState,
            onCheckedChange = { isChecked ->
                switchState = isChecked
                onSwitchStateChanged(isChecked)
            },
            thumbContent = {
                Icon(
                    imageVector = if (switchState) {
                        Icons.Default.Check
                    } else {
                        Icons.Default.Close
                    }, tint = Color.White,
                    contentDescription = null
                )
            }
        )
    }
}
