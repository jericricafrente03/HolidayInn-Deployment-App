package com.bittelasia.holidayinn.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.bittelasia.holidayinn.ui.theme.fontFamilyDefault

@Composable
fun TextFormat(
    value: String?,
    color: String?,
    modifier: Modifier = Modifier,
    size: TextUnit = 23.sp,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    if (value != null){
        Text(
            text = value,
            textAlign = textAlign,
            modifier = modifier,
            color = Color(android.graphics.Color.parseColor(color ?: defaultColor)),
            fontSize = size,
            fontFamily = fontFamilyDefault,
            fontWeight = fontWeight
        )
    }
}


@Composable
fun TextFormatDescription(
    value: String?,
    color: String?,
    modifier: Modifier = Modifier,
    size: TextUnit = 23.sp,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int,
    lineHeight: TextUnit = 28.sp,
) {
    if (value != null){
        Text(
            text = value,
            textAlign = textAlign,
            modifier = modifier,
            color = Color(android.graphics.Color.parseColor(color ?: defaultColor)),
            fontSize = size,
            fontFamily = fontFamilyDefault,
            fontWeight = fontWeight,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
           lineHeight = lineHeight
        )
    }
}