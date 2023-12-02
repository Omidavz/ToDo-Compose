package com.omidavz.todocompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.omidavz.todocompose.ui.screens.list.ListScreen
import com.omidavz.todocompose.ui.viewmodels.SharedViewModel
import com.omidavz.todocompose.util.Action
import com.omidavz.todocompose.util.Constants.LIST_ARGUMENT_KEY
import com.omidavz.todocompose.util.Constants.LIST_SCREEN
import com.omidavz.todocompose.util.toAction

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        var myAction by rememberSaveable{
            mutableStateOf(Action.NO_ACTION)
        }

        LaunchedEffect(key1 = myAction) {
            if (action != myAction) {
                myAction = action
            }
            sharedViewModel.updateAction(newAction = action)
        }

        val databaseAction = sharedViewModel.action




        ListScreen(
            action = databaseAction,
            navigateToTasksScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )

    }


}