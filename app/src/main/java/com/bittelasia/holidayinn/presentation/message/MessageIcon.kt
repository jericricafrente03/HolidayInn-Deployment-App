package com.bittelasia.holidayinn.presentation.message

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.CustomDivider
import com.bittelasia.holidayinn.presentation.components.TextFormatDescription
import com.bittelasia.holidayinn.presentation.components.dividerColor
import com.bittelasia.holidayinn.presentation.components.getMessageStatus
import com.bittelasia.holidayinn.presentation.components.parseColor

@Composable
fun MessageIcon(
    zone: Zone?,
    messageData: GetMessageData?,
    onMenuSelected: ((menuItem: GetMessageData) -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (isFocused) parseColor(color = zone?.text_color) else Color.Transparent,
        animationSpec = tween(durationMillis = 200), label = ""
    )
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        Box(
            modifier = Modifier
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        if (messageData != null) {
                            onMenuSelected?.invoke(messageData)
                        }
                    }
                )
                .drawBehind {
                    drawRect(color)
                }
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(getMessageStatus(messageData?.messg_status))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextFormatDescription(
                        value = messageData?.messg_from,
                        color = if (!isFocused) zone?.text_color else zone?.text_selected,
                        size = 25.sp,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                    TextFormatDescription(
                        value = messageData?.messg_subject,
                        color = if (!isFocused) zone?.text_color else zone?.text_selected,
                        size = 23.sp,
                        maxLines = 1
                    )
                }
            }
        }
        CustomDivider(
            color = dividerColor,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
    }

}