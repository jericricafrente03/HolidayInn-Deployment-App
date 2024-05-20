package com.bittelasia.holidayinn.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bittelasia.holidayinn.R
import com.bittelasia.holidayinn.data.manager.XMPPManager
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.ui.theme.LightBlue
import com.bittelasia.holidayinn.ui.theme.PurpleGrey40
import eu.chainfire.libsuperuser.BuildConfig

@Composable
fun CustomDialog(openDialogCustom: MutableState<Boolean>, onExitClick: () -> Unit) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(openDialogCustom = openDialogCustom) {
            onExitClick()
        }
    }
}

@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    onExitClick: () -> Unit,
) {
    val stateFlow by XMPPManager.stateFlow.collectAsState()
    Card(
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.meshlogo),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                CustomList("Room No. ", STB.ROOM)
                CustomList("Mac Address ", STB.MAC_ADDRESS)
                CustomList(
                    "Connection Status ",
                    value = stateFlow.name,
                    color = if (stateFlow.name != "Authenticated") Color.Red else Color.Green
                )
                CustomList("Version ", BuildConfig.VERSION_NAME)

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp), horizontalArrangement = Arrangement.End
            ) {

                Button(onClick = {
                    openDialogCustom.value = false
                    onExitClick()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                            shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        modifier = Modifier.width(100.dp),
                        text = "HIDE",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                        ),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CustomList(title: String, value: String, color: Color = PurpleGrey40) {
    Row {
        Text(
            modifier = Modifier.weight(1F),
            text = title,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
            ),
            color = PurpleGrey40,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
        )
        Text(
            modifier = Modifier.weight(1F),
            text = value,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
            ),
            color = color,
            textAlign = TextAlign.End,
            fontSize = 20.sp
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun MyDialogUIPreview() {
    CustomDialogUI(openDialogCustom = mutableStateOf(false)) {}
}
