package com.omidavz.todocompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omidavz.todocompose.util.Constants.DATABASE_TABLE

@Entity(DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority

)
