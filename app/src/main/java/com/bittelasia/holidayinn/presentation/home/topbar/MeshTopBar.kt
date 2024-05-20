package com.bittelasia.holidayinn.presentation.home.topbar

import android.graphics.Typeface
import android.widget.TextClock
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import com.bittelasia.holidayinn.ui.theme.fontFamilyDefault

@Composable
fun MeshTopBar(
    viewModel: ApplicationViewModel
) {
    val themeLogo by viewModel.themeLogo.collectAsState()
    val themeTime by viewModel.themeTimeWeather.collectAsState()
    val themeGuest by viewModel.themeGuest.collectAsState()
    val selectedConfig by viewModel.selectedConfig.collectAsState()


    Theme(zone = themeGuest, modifier = Modifier.height(120.dp)) {
        Row {
            Theme(
                zone = themeLogo,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 50.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                val context = LocalContext.current
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(selectedConfig?.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(vertical = 13.dp)
                )
            }
            Theme(
                zone = themeTime,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 50.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                themeTime?.text_color?.let {
                    CalendarSection(color = it)
                }
            }
        }
    }
}

@Composable
fun CalendarSection(
    color: String?,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle()
) {
    val resolver = LocalFontFamilyResolver.current
    val face: Typeface = remember(resolver, style) {
        resolver.resolve(
            fontFamily = fontFamilyDefault,
            fontWeight = FontWeight.Bold
        )
    }.value as Typeface

    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                format12Hour?.let {
                    this.format12Hour = "EE | MM/dd/yyy | hh:mm a"
                }
                timeZone?.let {
                    this.timeZone = it
                }
                textSize.let {
                    this.textSize = 25f
                }
                typeface = face
                color?.toColorInt()?.let { setTextColor(it) }
            }
        }, modifier = modifier
    )
}