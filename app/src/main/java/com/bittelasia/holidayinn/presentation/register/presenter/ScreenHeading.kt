package com.bittelasia.holidayinn.presentation.register.presenter

import androidx.tv.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ScreenHeading(heading: String) {
    Text(
        text = heading, style = TextStyle(
            fontSize = 55.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Thin,
            color = Color.White
        )
    )
}

@Preview
@Composable
fun ScreenHeadingPrev() {
    ScreenHeading("LOGIN")
}