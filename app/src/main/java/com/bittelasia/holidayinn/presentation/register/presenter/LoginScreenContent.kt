package com.bittelasia.holidayinn.presentation.register.presenter

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.bittelasia.holidayinn.presentation.register.data.RegistrationFormEvent
import com.bittelasia.holidayinn.presentation.register.data.RegistrationFormState

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun LoginScreenContent(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigateToHomeScreen: () -> Unit = {},
    onNavigateToPing:() -> Unit = {},
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val events = remember(viewModel.validationEvents, lifecycleOwner) {
        viewModel.validationEvents.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val state = viewModel.state
    LaunchedEffect(key1 = true) {
        events.collect { event ->
            when (event) {
                is RegistrationViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Registration successful",
                        Toast.LENGTH_LONG
                    ).show()
                    onNavigateToHomeScreen()
                }

                is RegistrationViewModel.ValidationEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.messageId,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is RegistrationViewModel.ValidationEvent.Loading -> {
                    Toast.makeText(
                        context,
                        "Validating ...",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        RegisterScreen(
            event = viewModel::onEvent,
            state = state,
            onNavigateToPing = onNavigateToPing
        )
    }
}


@Preview
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(event = {}, state = RegistrationFormState())
}
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    event: (RegistrationFormEvent) -> Unit,
    state: RegistrationFormState,
    onNavigateToPing:() -> Unit = {}) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeading(heading = "IPTV")
            Spacer(modifier = Modifier.height(20.dp))

            TvTextField(
                value = state.ip,
                onValueChange = {
                    event(RegistrationFormEvent.IpChanged(it))
                },
                isError = state.ipErr != null,
                label = "IP ADDRESS"
            )
            if (state.ipErr != null) {
                TextError(value = state.ipErr, modifier = Modifier.align(Alignment.End))
            }
            Spacer(modifier = Modifier.height(20.dp))
            TvTextField(
                value = state.port,
                label = "PORT",
                onValueChange = {
                    event(RegistrationFormEvent.PortChange(it))
                },
                isError = state.portErr != null
            )
            if (state.portErr != null) {
                TextError(value = state.portErr, modifier = Modifier.align(Alignment.End))
            }
            Spacer(modifier = Modifier.height(20.dp))
            TvTextField(
                value = state.room,
                label = "ROOM",
                onValueChange = {
                    event(RegistrationFormEvent.RoomChanged(it))
                },
                isError = state.roomErr != null
            )
            if (state.roomErr != null) {
                TextError(value = state.roomErr, modifier = Modifier.align(Alignment.End))
            }

            Spacer(modifier = Modifier.height(20.dp))


            LoginButton(
                onClick = { event(RegistrationFormEvent.Submit) },
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "LOGIN",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = { onNavigateToPing() }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "DIAGNOSE SERVER CONNECTION",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}
