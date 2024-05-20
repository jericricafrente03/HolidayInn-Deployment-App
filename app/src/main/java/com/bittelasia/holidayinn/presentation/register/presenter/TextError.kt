package com.bittelasia.holidayinn.presentation.register.presenter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TextError(
    value: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = value,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}