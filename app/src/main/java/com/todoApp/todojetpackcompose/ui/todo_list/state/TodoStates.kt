package com.todoApp.todojetpackcompose.ui.todo_list.state

import com.todoApp.todojetpackcompose.models.TodoListItem

data class TodoStates(
    val data:List<TodoListItem> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)