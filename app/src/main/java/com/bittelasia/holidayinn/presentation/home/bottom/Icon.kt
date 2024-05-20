package com.bittelasia.holidayinn.presentation.home.bottom

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.TextFormat
import com.bittelasia.holidayinn.presentation.components.capitalizeAllLetters

@Composable
fun IconItem(
    modifier: Modifier = Modifier,
    menuItem: AppData,
    zone: Zone,
    onMenuSelected: ((menuItem: AppData) -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val context = LocalContext.current
    var isFocused by remember { mutableStateOf(false) }
    val iconBaseURL = "${STB.HOST}:${STB.PORT}"
    val offsetY =
        animateDpAsState(targetValue = if (isFocused || isHovered) ((-8).dp) else 0.dp, label = "")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onFocusChanged { isFocused = it.isFocused }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onMenuSelected?.invoke(menuItem)
                }
            )
            .padding(top = 15.dp)
            .offset {
                IntOffset(0, offsetY.value.roundToPx())
            }
    ) {
        val resultFocus = if (!isFocused) menuItem.icon else menuItem.icon_selected
        AsyncImage(
            model = ImageRequest.Builder(context)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .data(iconBaseURL.plus(resultFocus))
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextFormat(
            value = menuItem.display_name?.let { capitalizeAllLetters(input = it) },
            color = zone.text_color,
            size = 30.sp,
            modifier = Modifier
                .graphicsLayer {
                    alpha = if (isFocused || isHovered) 1.0f else 0f
                }
        )
    }

}