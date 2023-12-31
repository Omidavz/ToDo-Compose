package com.omidavz.todocompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.omidavz.todocompose.ui.screens.task.TaskScreen
import com.omidavz.todocompose.ui.viewmodels.SharedViewModel
import com.omidavz.todocompose.util.Action
import com.omidavz.todocompose.util.Constants
import com.omidavz.todocompose.util.Constants.TASK_ARGUMENT_KEY

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToTListScreen: (Action) -> Unit
) {

    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        }),
//        enterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { fullWidth -> -fullWidth  },
//                animationSpec = tween(
//                    delayMillis = 600
//                )
//            )
//        }

    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }
        val selectedTask by sharedViewModel.selectedTask.collectAsState()


        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                sharedViewModel.updateTaskFields(selectedTask = selectedTask)

            }
        }


        TaskScreen(
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel,
            navigateToListScreens = navigateToTListScreen
        )

    }


}