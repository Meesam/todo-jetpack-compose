package com.todoApp.todojetpackcompose.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.ui.todo_list.events.TodoListEvent
import com.todoApp.todojetpackcompose.util.ApiState
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: TodosViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(false) }
    val todos = viewModel.getTodoEventFlow.value
    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
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
        viewModel.onEvent(TodoListEvent.GetNoteEvent)
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
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo App")
                }, colors = TopAppBarDefaults.topAppBarColors(
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
                    contentColor = Color.Black
                )
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        },

    ) { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {
            if(todos.isLoading || isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }

            }
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


@Composable
fun TodoListItemScreen(todo:TodoListItem,
                       onDelete :()->Unit
                       //onUpdate:(todo:TodoListItem,isDone:Boolean)->Unit
)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(MaterialTheme.colorScheme.primaryContainer)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = todo.title)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    onDelete()
                }
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                }
            }

        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 12.dp, bottom = 10.dp)
        ) {
            Text(text = todo.description)
        }

    }
}



/*@Preview(showBackground = true)
@Composable
fun TodoListItemScreenPreview(){
    TodoListItemScreen1()
}*/