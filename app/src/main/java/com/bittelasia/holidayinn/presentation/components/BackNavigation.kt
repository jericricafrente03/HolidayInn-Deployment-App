package com.bittelasia.holidayinn.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.navigation.NestedScreens

@Composable
fun CustomBackButton(
    navHostController: NavHostController,
) {
    TransparentBorder(
        onClick = { navHostController.navigate(NestedScreens.Home.title) },
        Modifier.padding(horizontal = 30.dp, vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.chevron_back_circle_icon_235141),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clickable(
                    onClick = { navHostController.navigate(NestedScreens.Home.title) }
                )
        )
    }
}

