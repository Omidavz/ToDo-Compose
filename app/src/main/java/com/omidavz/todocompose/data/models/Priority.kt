package com.omidavz.todocompose.data.models

import androidx.compose.ui.graphics.Color
import com.omidavz.todocompose.ui.theme.HighPriorityColor
import com.omidavz.todocompose.ui.theme.LowPriorityColor
import com.omidavz.todocompose.ui.theme.MediumPriorityColor
import com.omidavz.todocompose.ui.theme.NonePriorityColor

enum class Priority(
    val color: Color
) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}