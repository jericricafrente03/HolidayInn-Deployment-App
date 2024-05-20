package com.bittelasia.holidayinn.presentation.hotelinfo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.CustomBackButton
import com.bittelasia.holidayinn.presentation.components.TextFormat
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.components.parseColor
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import kotlinx.coroutines.delay

@Composable
fun InfoListData(
    modifier: Modifier = Modifier,
    category: List<FacilitiesData>?,
    zone: Zone?,
    navController: NavHostController,
    viewModel: ApplicationViewModel
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(Unit) {
        delay(500)
        if (category?.isNotEmpty() == true)
            focusRequester.requestFocus()
    }

    Theme(zone = zone, modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            TextFormat(
                value = "HOTEL INFORMATION",
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
            LazyColumn(
                modifier = Modifier
                    .weight(.8f)
            ) {
                category?.let { data ->
                    items(data.size) { list ->
                        val item = data[list]
                        HotelCategoryName(
                            zone = zone,
                            facilitiesData = item,
                            modifier = Modifier
                                .then(
                                    if (list == 0)
                                        Modifier.focusRequester(focusRequester)
                                    else
                                        Modifier
                                )
                        ) {
                            viewModel.selecteFacilities(it.id)
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

@Composable
fun HotelCategoryName(
    modifier: Modifier = Modifier,
    zone: Zone?,
    facilitiesData: FacilitiesData?,
    onMenuSelected: ((menuItem: FacilitiesData) -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    var isFocused by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (isFocused || isHovered) parseColor(color = zone?.text_color) else Color.Transparent,
        animationSpec = tween(durationMillis = 200), label = ""
    )
    Box(
        modifier = modifier
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (facilitiesData != null) {
                        onMenuSelected?.invoke(facilitiesData)
                    }
                }
            )
            .drawBehind {
                drawRect(color)
            }
    ) {
        TextFormat(
            value = facilitiesData?.item_name,
            color = if (isFocused || isHovered) zone?.text_selected else zone?.text_color,
            size = 28.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .fillMaxWidth()
        )
    }
}



