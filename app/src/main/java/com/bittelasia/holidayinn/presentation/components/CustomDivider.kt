package com.bittelasia.holidayinn.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(color: String?, modifier: Modifier = Modifier){
    HorizontalDivider(
        modifier = modifier
            .height(80.dp)
            .width(1.dp),
        color = Color(android.graphics.Color.parseColor(color ?: defaultColor))
    )
}

@Composable
fun HomePageDivider(color: String?, modifier: Modifier = Modifier){
    HorizontalDivider(
        color = Color(android.graphics.Color.parseColor(color ?: defaultColor)),
        modifier = modifier
            .fillMaxHeight()
            .width(2.dp)
    )
}
@Preview
@Composable
fun View(){
    CustomDivider(color = "#000000")
}