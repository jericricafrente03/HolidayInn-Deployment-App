package com.bittelasia.holidayinn.presentation.home.topbar

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.components.defaultColor
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import com.bittelasia.holidayinn.ui.theme.fontFamilyDefault

@Composable
fun HomePageContent(viewModel: ApplicationViewModel) {
    val message by viewModel.selectedConfig.collectAsState()
    val messageZone by viewModel.themeWelcome.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Theme(zone = messageZone, contentAlignment = Alignment.BottomStart) {
            message?.let {
                it.welcome_message?.let { message ->
                    HtmlTextView(html = message, zone = messageZone)
                }
            }
        }
    }
}

@Composable
fun HtmlTextView(html: String?, zone: Zone?) {
    val parsedHtml = Html.fromHtml(html, FROM_HTML_MODE_LEGACY)
    Text(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 20.dp),
        text = parsedHtml.trim().toString(),
        color = Color(android.graphics.Color.parseColor(zone?.text_color ?: defaultColor)),
        fontSize = 80.sp,
        fontFamily = fontFamilyDefault,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    )
}