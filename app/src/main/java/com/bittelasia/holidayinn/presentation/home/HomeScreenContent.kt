package com.bittelasia.holidayinn.presentation.home

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bittelasia.holidayinn.data.manager.XMPPManager
import com.bittelasia.holidayinn.navigation.ScreenNavigation
import com.bittelasia.holidayinn.presentation.home.bottom.BottomMenu
import com.bittelasia.holidayinn.presentation.home.main.MainPage
import com.bittelasia.holidayinn.presentation.message.Message
import com.bittelasia.holidayinn.presentation.tv.activity.TVPlayerActivity


@Composable
fun DashboardScreen() {

    val homeViewModel = hiltViewModel<ApplicationViewModel>()
    val bg by homeViewModel.selectedBg.collectAsState()
    val appListTheme by homeViewModel.themeApplist.collectAsState()
    val appList by homeViewModel.selectedAppItem.collectAsState()
    val navController = rememberNavController()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    LaunchedEffect(true) {
        XMPPManager.createConnection()
        homeViewModel.allApiFlow()
    }

    var currentDestination: String? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentDestination = destination.route
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    val constraintContent = ConstraintSet {
        val mainPage = createRefFor("mainPage")
        val homePageTop = createRefFor("homePageTop")
        val homePageBottom = createRefFor("homePageBottom")
        val homeMessage = createRefFor("homeMessage")

        constrain(mainPage) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(homePageTop) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(120.dp)
        }
        constrain(homeMessage) {
            top.linkTo(homePageTop.bottom)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            bottom.linkTo(homePageBottom.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(homePageBottom) {
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.value(200.dp)
        }
    }
    ConstraintLayout(constraintContent, Modifier.fillMaxSize()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestinations = navBackStackEntry?.destination
        val bottomBarDestination = appList?.any {
            it.display_name == currentDestinations?.route
        }
        if (bottomBarDestination == false) {
            if (currentDestinations?.route == "home") {
                MainPage(theme = bg, modifier = Modifier.layoutId("mainPage"))
                Message(viewModel = homeViewModel, modifier = Modifier.layoutId("homeMessage"))
                TopSection(modifier = Modifier.layoutId("homePageTop"))
                BottomMenu(
                    modifier = Modifier.layoutId("homePageBottom"),
                    appData = appList,
                    zone = appListTheme
                ) {
                    if (it.display_name == "TV") {
                        val intent = Intent(context, TVPlayerActivity::class.java)
                        launcher.launch(intent)
                    } else {
                        it.display_name?.let { id -> navController.navigate(id) }
                    }
                }
                BackHandler {}
            }
        }
        ScreenNavigation(navController = navController, viewModel = homeViewModel)
    }
}

@Composable
fun TopSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {}
}
