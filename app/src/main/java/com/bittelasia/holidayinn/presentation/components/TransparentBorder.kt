package com.bittelasia.holidayinn.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TransparentBorder(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    Surface(
        onClick = { onClick() },
        modifier = modifier,
        colors = ClickableSurfaceDefaults.colors(
            containerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        shape = ClickableSurfaceDefaults.shape(
            shape = CircleShape
        ),
        scale = ClickableSurfaceDefaults.scale(focusedScale = 1.2f)
    ) {
        content()
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Preview
@Composable
private fun BorderedFocusableItemPrev() {
    TransparentBorder(onClick = {}) {
        Text(text = "Preview Text")
    }
}