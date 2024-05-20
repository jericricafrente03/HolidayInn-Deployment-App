package com.bittelasia.holidayinn.presentation.hotelinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.presentation.message.PreviewNoMessage

@Composable
fun InfoItemData(
    modifier: Modifier = Modifier,
    hotelItem: FacilitiesData?,
    zone: Zone?
) {

    Box(modifier = modifier.background(Color.White), contentAlignment = Center) {
        val context = LocalContext.current
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(hotelItem?.img_uri)
                .build()
        )
        val painterState = painter.state
        if (painterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        } else {
            PreviewNoMessage(zone = zone, value = "Select an Image to View")
        }
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}
