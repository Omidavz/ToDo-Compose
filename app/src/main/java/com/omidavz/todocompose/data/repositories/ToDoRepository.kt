package com.omidavz.todocompose.data.repositories

import com.omidavz.todocompose.data.ToDoDao
import com.omidavz.todocompose.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTasks : Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority : Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority : Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()


    fun getSelectedTask(taskId : Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask){
        return toDoDao.addTask(toDoTask)
    }
    suspend fun update(toDoTask: ToDoTask){
        return toDoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask){
        return toDoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTasks(){
        return toDoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery : String) : Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery)
    }


}