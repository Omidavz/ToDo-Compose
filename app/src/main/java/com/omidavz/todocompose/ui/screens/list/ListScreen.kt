package com.omidavz.todocompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.omidavz.todocompose.R
import com.omidavz.todocompose.ui.viewmodels.SharedViewModel
import com.omidavz.todocompose.util.Action
import com.omidavz.todocompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun ListScreen(
    action: Action,
    navigateToTasksScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {


    LaunchedEffect(key1 = action){
        sharedViewModel.handleDatabaseActions(action = action)
    }


    val searchAppBarState: SearchAppBarState = sharedViewModel.searchAppBarState
    val searchTextState: String = sharedViewModel.searchTextState
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState ,
        onComplete = { sharedViewModel.updateAction(newAction = it) },
        onUndoClicked = {
            sharedViewModel.updateAction(newAction = it)
        },
        taskTitle = sharedViewModel.title,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks=highPriorityTasks,
                sortState=sortState,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = {action, task ->
                    sharedViewModel.updateAction(newAction = action)
//                    sharedViewModel.action = action
                    sharedViewModel.updateTaskFields(selectedTask = task )
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                navigateToTaskScreen = navigateToTasksScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTasksScreen)

        },
        backgroundColor = MaterialTheme.colorScheme.surface

    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White,
        )

    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle : String,
    action: Action
) {

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action){
        if (action != Action.NO_ACTION){
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, taskTitle = taskTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedTask(
                    action = action ,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )

            }
            onComplete(Action.NO_ACTION)
        }
    }

}
private fun setMessage(action: Action,taskTitle : String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed."
        else -> "${action.name}: $taskTitle"
    }

}

private fun setActionLabel(action: Action) : String{
    return if (action.name == "DELETE"){
        "UNDO"
    }else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult : SnackbarResult,
    onUndoClicked : (Action) -> Unit
){
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
        ){
        onUndoClicked(Action.UNDO)
    }

}



