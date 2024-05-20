package com.bittelasia.holidayinn.presentation.message

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel

@Composable
fun MessageContent(
    viewModel: ApplicationViewModel,
    navHostController: NavHostController
) {
    MessageData(
        modifier = Modifier
            .fillMaxSize(),
        viewModel = viewModel,
        navHostController = navHostController
    )
}