package com.bittelasia.holidayinn.presentation.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.CustomDivider
import com.bittelasia.holidayinn.presentation.components.TextFormat
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.components.customDate
import com.bittelasia.holidayinn.presentation.components.dividerColor
import com.bittelasia.holidayinn.presentation.components.processSpecialCharacters
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel

@Composable
fun MessageInfo(
    modifier: Modifier = Modifier,
    viewModel: ApplicationViewModel,
    zone: Zone?
) {
    val messageInfo by viewModel.selectedMessageInfo.collectAsState()

    Theme(zone = zone, modifier = modifier) {
        val constraint = ConstraintSet {
            val headerMessage = createRefFor("headerMessage")
            val bodyMessage = createRefFor("bodyMessage")
            val messageGuide = createRefFor("messageGuideline")
            val previewMessage = createRefFor("preview")
            val guideline = createGuidelineFromTop(0.15f)
            constrain(headerMessage) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(guideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            constrain(messageGuide) {
                top.linkTo(headerMessage.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                width = Dimension.fillToConstraints
                height = Dimension.value(1.dp)
            }
            constrain(bodyMessage) {
                top.linkTo(guideline)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            constrain(previewMessage) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
        ConstraintLayout(constraint, modifier = Modifier
            .fillMaxSize().padding(10.dp)) {
            if (messageInfo != null) {
                MessageIcon(
                    modifier = Modifier.layoutId("headerMessage"),
                    messageData = messageInfo,
                    zone = zone?.text_color
                )
                CustomDivider(
                    color = dividerColor,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .layoutId("messageGuideline")
                )
                MessageInfoContent(
                    messageData = messageInfo,
                    zone = zone,
                    modifier = Modifier.layoutId("bodyMessage")
                )
            } else {
                PreviewNoMessage(modifier = Modifier.layoutId("preview"), zone = zone, value = "Select message")
            }
        }
    }
}

@Composable
fun MessageInfoContent(messageData: GetMessageData?, zone: Zone?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        TextFormat(value = "Hi Ma'am/Sir. ", color = zone?.text_color)
        Spacer(modifier = Modifier.height(10.dp))
        TextFormat(
            value = removeHtmlTags(processSpecialCharacters(messageData?.messg_text))  ,
            color = zone?.text_color
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFormat(value = "Sincerely yours,", color = zone?.text_color)
        TextFormat(value = "Front Desk,", color = zone?.text_color)
    }
}

@Composable
fun MessageIcon(messageData: GetMessageData?, zone: String?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(100.dp),
            painter = painterResource(id = R.drawable.admin),
            contentDescription = null
        )
        Spacer(Modifier.width(15.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            TextFormat(value = "From :".plus(messageData?.messg_from), color = zone, size = 25.sp)
            TextFormat(
                value = customDate(
                    oldDate = messageData?.messg_datetime,
                    oldFormat = "yyyy-MM-dd HH:mm:ss",
                    newFormat = "hh:mm aa | MMM dd yyyy"
                ), color = zone,
                size = 23.sp
            )
        }
    }
}

@Composable
fun PreviewNoMessage(value : String?, modifier: Modifier = Modifier, zone: Zone?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        TextFormat(value = value, color = zone?.text_color)
    }
}

fun removeHtmlTags(input: String?): String? {
    val regex = """<[^>]+>""".toRegex()
    return input?.let { regex.replace(it, "") }
}