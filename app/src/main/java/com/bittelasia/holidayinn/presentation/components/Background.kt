package com.bittelasia.holidayinn.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.domain.model.theme.item.Zone

@Composable
fun Theme(
    modifier: Modifier = Modifier,
    zone: Zone?,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (BoxScope.() -> Unit)
) {
    val myBaseURL = STB.HOST+":"+ STB.PORT
    Box(modifier = modifier, contentAlignment = contentAlignment) {
        if (zone != null){
            ThemeBackground(url = myBaseURL.plus(zone.url))
            content()
        }
    }
}

@Composable
fun ThemeBackground(
    modifier: Modifier = Modifier,
    url: String?,
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    val context = LocalContext.current
    val imageUrl = url ?: '0'
    AsyncImage(
        model = ImageRequest.Builder(context)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = contentScale
    )
}
