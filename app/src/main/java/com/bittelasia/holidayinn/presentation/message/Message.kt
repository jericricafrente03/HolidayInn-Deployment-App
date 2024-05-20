package com.bittelasia.holidayinn.presentation.message

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import com.bittelasia.holidayinn.ui.theme.HolidayInnColor

@Composable
fun Message(modifier: Modifier = Modifier, viewModel: ApplicationViewModel) {
    val messageList by viewModel.messageNo.collectAsState()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ) {

        AnimatedVisibility(visible = messageList > 0) {
            Box(modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.unread_black),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(70.dp)
                )
                Box(
                    modifier = Modifier.size(83.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    CircleBackgroundText(
                        text = messageList.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun CircleBackgroundText(
    text: String
) {
    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        val circleSize = minOf(30.dp, 30.dp)
        Box(
            modifier = Modifier
                .size(circleSize)
                .background(HolidayInnColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

