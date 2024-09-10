package com.todoApp.todojetpackcompose.ui.add_edit_todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoApp.todojetpackcompose.ui.add_edit_todo.events.AddEditTodoEvent
import com.todoApp.todojetpackcompose.util.ApiState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    onPopBackStack:()->Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = true) {
        viewModel.addTodoEventFlow.collectLatest {
              when(it){
                is ApiState.Success->{
                    //onPopBackStack()
                    false
                }
                is ApiState.Failure ->{
                    //onPopBackStack()
                    false
                }
                is ApiState.Loading ->{
                    //onPopBackStack()
                    true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Todo")
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                      Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "")

                    }
                }
            )
        },

        ) { paddingValue ->
        Box(modifier = Modifier
            .padding(paddingValue)
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                OutlinedTextField(value = viewModel.title.value, onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.onTitleTextChange(it))
                }, placeholder = {
                    Text("Please enter the text")
                })
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(value = viewModel.description.value, onValueChange = {

                    viewModel.onEvent(AddEditTodoEvent.onDescriptionChange(it))
                }, placeholder = {
                    Text("Please enter the description")
                }, singleLine = false, maxLines = 5)

                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(onClick = {
                    viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick(title = viewModel.title.value, description = viewModel.description.value))
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}