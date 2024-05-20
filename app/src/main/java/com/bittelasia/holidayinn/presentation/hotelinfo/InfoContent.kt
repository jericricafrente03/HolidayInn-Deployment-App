package com.bittelasia.holidayinn.presentation.hotelinfo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel

@Composable
fun InfoContent(
    viewModel: ApplicationViewModel,
    navHostController: NavHostController
) {
    InfoData(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        navHostController = navHostController
    )
}