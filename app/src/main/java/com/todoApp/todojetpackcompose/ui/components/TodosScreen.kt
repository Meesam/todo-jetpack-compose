package com.todoApp.todojetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.todoApp.todojetpackcompose.ui.authentication.UserLoginViewModel
import com.todoApp.todojetpackcompose.ui.navigation.BottomNavigation
import com.todoApp.todojetpackcompose.ui.todo_list.TodosViewModel
import com.todoApp.todojetpackcompose.ui.todo_list.events.TodoListEvent
import com.todoApp.todojetpackcompose.util.ApiState
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: TodosViewModel,
    eventType:String
)
    {
        var isLoading by remember { mutableStateOf(false) }
        val todos =  when(eventType){
            "all-todos" -> {
                viewModel.getTodoEventFlow.value
            }
             "all-completed-todos" -> {
                viewModel.getCompletedTodoEventFlow.value
            }
             "all-deleted-todos" ->{
                viewModel.getdeletedTodoEventFlow.value
            }
            else -> null
        }

        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(key1 = true) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }

                    is UiEvent.Navigate -> onNavigate(event)
                    else -> Unit
                }
            }
        }

        // To render Todo list
        LaunchedEffect(key1 = true) {
            when(eventType){
                "all-todos" -> {
                    viewModel.onEvent(TodoListEvent.GetNoteEvent)
                }
                "all-completed-todos" -> {
                    viewModel.onEvent(TodoListEvent.GetComopletedNoteEvent)
                }
                "all-deleted-todos" ->{
                    viewModel.onEvent(TodoListEvent.GetDeleteNoteEvent)
                }
                else -> viewModel.onEvent(TodoListEvent.GetNoteEvent)
            }
        }

        // to delete the todo
        LaunchedEffect(key1 = true) {
            viewModel.deleteTodoEventFlow.collectLatest {
                isLoading = when(it){
                    is ApiState.Success->{
                        viewModel.onEvent(TodoListEvent.GetNoteEvent)
                        false
                    }
                    is ApiState.Failure ->{
                        false
                    }
                    is ApiState.Loading ->{
                        true
                    }
                }
            }
        }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                TopAppBar(
                    title = {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp, top = 15.dp, bottom = 10.dp)
                        ) {
                            when(eventType){
                                "all-todos" -> {
                                    Text(text = "All Todos", color = Color.White)
                                }
                                "all-completed-todos" -> {
                                    Text(text = "Completed Todos", color = Color.White)
                                }
                                "all-deleted-todos" ->{
                                    Text(text = "Delete Todos", color = Color.White)
                                }
                                else -> Text(text = "All Todos", color = Color.White)
                            }

                        }

                    },
                    actions = {
                        UserSignOut(onNavigate)
                    }
                    , colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            floatingActionButton = {
                IconButton(
                    onClick = {
                        onNavigate(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
            },
            bottomBar = {
                BottomNavigation(onNavigate)
            }

        ) { paddingValue ->
            Box(modifier = Modifier.padding(paddingValue)) {
                if (todos != null) {
                    if(todos.isLoading){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                if (todos != null) {
                    if(todos.data.isNotEmpty()){
                        LazyColumn() {
                            items(todos.data, key = {
                                it.id
                            }) { it ->
                                TodoListItemScreen(todo =  it) {
                                    viewModel.onEvent(TodoListEvent.OnDeleteTodoClick(it))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
