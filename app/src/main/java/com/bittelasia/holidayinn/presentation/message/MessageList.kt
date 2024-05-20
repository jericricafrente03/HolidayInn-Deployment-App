package com.bittelasia.holidayinn.presentation.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.tv.foundation.lazy.list.TvLazyColumn
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.CustomBackButton
import com.bittelasia.holidayinn.presentation.components.TextFormat
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messageData: List<GetMessageData>?,
    zone: Zone?,
    navController: NavHostController,
    viewModel: ApplicationViewModel
) {
    Theme(zone = zone, modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            TextFormat(
                value = "MESSAGE",
                color = zone?.text_color,
                size = 30.sp,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
            TvLazyColumn(
                modifier = Modifier
                    .weight(.8f),
                horizontalAlignment = Alignment.Start,
            ) {
                messageData?.let { data ->
                    items(data.size) { list ->
                        val item = data[list]
                        MessageIcon(zone = zone, messageData = item) {
                            viewModel.guestMessage(it.id)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(.2f)
                    .fillMaxWidth(), contentAlignment = Alignment.CenterStart,
            ) {
                CustomBackButton(navHostController = navController)
            }
        }
    }
}
