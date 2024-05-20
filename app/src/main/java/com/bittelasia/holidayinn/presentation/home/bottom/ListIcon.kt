package com.bittelasia.holidayinn.presentation.home.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.details.CustomDialog
import kotlinx.coroutines.delay

@Composable
fun BottomMenu(
    modifier: Modifier = Modifier,
    appData: List<AppData>?,
    zone: Zone?,
    onMenuSelected: ((menuItem: AppData) -> Unit)? = null
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val displayDialog = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(500)
        focusRequester.requestFocus()
    }

    Theme(zone = zone, modifier = modifier.height(200.dp)) {
        TvLazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            appData?.let { data ->
                items(data.size) { list ->
                    val item = data[list]
                    IconItem(
                        menuItem = item,
                        zone = zone!!,
                        modifier = Modifier
                            .then(if (list == 0) Modifier.focusRequester(focusRequester) else Modifier)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyUp) {
                                    if (event.key.keyCode == 201863462912) {
                                        displayDialog.value = displayDialog.value != true
                                    }
                                }
                                false
                            }
                    ) {
                        onMenuSelected?.invoke(it)
                    }
                }
            }
        }
    }
    if (displayDialog.value) {
        CustomDialog(openDialogCustom = displayDialog) {}
    }
}