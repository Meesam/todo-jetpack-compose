package com.todoApp.todojetpackcompose.ui.todo_list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoApp.todojetpackcompose.ui.components.TodoListScreen
import com.todoApp.todojetpackcompose.util.UiEvent

@Composable
fun AllTodoList(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: TodosViewModel = hiltViewModel(),
    type:String
) {
    TodoListScreen(onNavigate, viewModel , type)
}



