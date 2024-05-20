package com.bittelasia.holidayinn.presentation.license

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.data.repository.stbpref.manager.LicenseDataStore.readLicense
import com.bittelasia.holidayinn.ui.theme.fontFamilyDefault
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonitorLicense() {
    val context = LocalContext.current
    var daysUntilExpiration by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        context.readLicense(Dispatchers.IO) { licenseInfo ->
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss")
            val licenseDate = LocalDate.parse(licenseInfo.END_DATE, formatter)
            val currentDate = LocalDate.now()
            daysUntilExpiration = ChronoUnit.DAYS.between(currentDate, licenseDate)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            daysUntilExpiration == 0L -> {
                ExpiredSubscriptionMessage()
            }
            daysUntilExpiration in 1L..30L -> {
                SubscriptionExpiryWarning(daysUntilExpiration!!)
            }
            daysUntilExpiration != null && daysUntilExpiration!! < 0L -> {
                ExpiredSubscriptionMessage()
            }
            else -> {}
        }
    }
}



@Composable
private fun ExpiredSubscriptionMessage() {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.warn),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Subscription expired",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = fontFamilyDefault,
                    fontSize = 35.sp
                )
            )
        }
    }
}

@Composable
private fun SubscriptionExpiryWarning(days: Long) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(120.dp))
        Text(
            text = "Subscription will expire in $days day(s).",
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#6D000000")))
                .fillMaxWidth(),
            style = TextStyle(
                color = Color.White,
                fontFamily = fontFamilyDefault,
                fontSize = 35.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}
