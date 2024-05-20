package com.bittelasia.holidayinn.presentation.message

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.navigation.NestedScreens
import com.bittelasia.holidayinn.presentation.components.HomePageDivider
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MessageData(
    modifier: Modifier = Modifier,
    viewModel: ApplicationViewModel,
    navHostController: NavHostController
) {
    val messageTheme by viewModel.themeMessageList.collectAsState()
    val messageDataTheme by viewModel.themeMessageData.collectAsState()
    val messageInfo by viewModel.selectedMessageData.collectAsState()
    val idle by viewModel.selectedConfig.collectAsState()

    var keyEventDetected by remember { mutableStateOf(false) }
    var timer by remember { mutableIntStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        viewModel.getMessage()
    }

    DisposableEffect(Unit) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                timerJob?.cancel()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                timerJob = CoroutineScope(Dispatchers.Main).launch {
                    idle?.let {
                        val counterTimer = (it.max_idle?.toInt()?.times(60))
                        while (timer < counterTimer!!) {
                            delay(1000)
                            if (keyEventDetected) {
                                keyEventDetected = false
                                timer = 0
                            } else {
                                timer++
                            }
                        }
                        navHostController.navigate(NestedScreens.Home.title)
                    }
                }
            }
        }
        (context as? ComponentActivity)?.lifecycle?.addObserver(lifecycleObserver)
        onDispose {
            (context as? ComponentActivity)?.lifecycle?.removeObserver(lifecycleObserver)
            timerJob?.cancel()
        }
    }

    Box(
        modifier = modifier
    ) {
        val constraint = ConstraintSet {
            val messageList = createRefFor("messageList")
            val messageData = createRefFor("messageData")
            val border = createRefFor("border")
            val guideline = createGuidelineFromStart(0.362f)

            constrain(messageList) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(guideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            constrain(border){
                start.linkTo(messageList.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.value(3.dp)
                height = Dimension.fillToConstraints
            }
            constrain(messageData) {
                top.linkTo(parent.top)
                start.linkTo(border.end)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
        ConstraintLayout(constraint, modifier = Modifier
            .fillMaxSize()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    keyEventDetected = true
                }
                false
            }
        ) {
            MessageList(
                modifier = Modifier.layoutId("messageList"),
                zone = messageTheme,
                messageData = messageInfo,
                navController = navHostController,
                viewModel = viewModel
            )
            HomePageDivider(color = messageTheme?.text_color, modifier = Modifier.layoutId("border"))
            MessageInfo(
                modifier = Modifier.layoutId("messageData"),
                viewModel = viewModel,
                zone = messageDataTheme
            )
        }
    }
}

