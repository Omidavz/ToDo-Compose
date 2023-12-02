package com.omidavz.todocompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.omidavz.todocompose.navigation.destinations.listComposable
import com.omidavz.todocompose.navigation.destinations.splashComposable
import com.omidavz.todocompose.navigation.destinations.taskComposable
import com.omidavz.todocompose.ui.viewmodels.SharedViewModel
import com.omidavz.todocompose.util.Constants.SPLASH_SCREEN

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    AnimatedNavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        splashComposable(
            navigateToTaskScreen = screen.splash
        )

        listComposable(
            navigateToTaskScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToTListScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
    }

}

