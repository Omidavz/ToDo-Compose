package com.omidavz.todocompose.ui.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.omidavz.todocompose.data.models.Priority
import com.omidavz.todocompose.data.models.ToDoTask
import com.omidavz.todocompose.ui.viewmodels.SharedViewModel
import com.omidavz.todocompose.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreens: (Action) -> Unit

) {

    val title: String = sharedViewModel.title
    val description: String = sharedViewModel.description
    val priority: Priority = sharedViewModel.priority

    val context = LocalContext.current


//    BackHandler(onBackPressed = { navigateToListScreens(Action.NO_ACTION) })
    BackHandler{
        navigateToListScreens(Action.NO_ACTION)
    }


    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreens = {action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreens(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreens(action)
                        } else {
                            displayToast(context = context)

                        }
                    }


                }
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChanged = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChanged = {
                    sharedViewModel.updateDescription(newDescription = it)

                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.updatePriority(newPriority = it)

                }
            )
        },

        backgroundColor = MaterialTheme.colorScheme.background

    )

}

fun displayToast(context : Context) {
    Toast.makeText(
        context,
        "Fields Empty",
        Toast.LENGTH_SHORT
    ).show()

}

//@Composable
//fun BackHandler(
//    backDispatcher : OnBackPressedDispatcher? =
//        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
//    onBackPressed : () -> Unit
//) {
//    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
//
//    val backCallBack = remember {
//        object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                currentOnBackPressed()
//            }
//
//        }
//    }
//
//    DisposableEffect(key1 = backDispatcher){
//        backDispatcher?.addCallback(backCallBack)
//
//        onDispose{
//            backCallBack.remove()
//        }
//    }
//
//
//
//}
