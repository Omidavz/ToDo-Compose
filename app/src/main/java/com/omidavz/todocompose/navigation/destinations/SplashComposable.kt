package com.omidavz.todocompose.navigation.destinations


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.omidavz.todocompose.ui.screens.splash.SplashScreen
import com.omidavz.todocompose.util.Constants.SPLASH_SCREEN

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit
) {

    composable(
        route = SPLASH_SCREEN,
//        exitTransition = {
//            slideOutVertically(
//                targetOffsetY = { fullWidth -> -fullWidth },
//                animationSpec = tween(durationMillis = 300)
//            )
//        }
    ) {
        SplashScreen(navigateToListScreen = navigateToTaskScreen)


    }


}
