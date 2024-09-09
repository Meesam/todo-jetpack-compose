package com.todoApp.todojetpackcompose.ui.add_edit_todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import com.todoApp.todojetpackcompose.ui.todo_list.TodoListItemScreen
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    onPopBackStack:()->Unit

){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Todo")
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                      Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "")

                    }
                }
            )
        },

        ) { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {
            Text(text = "Add Edit todo")
        }
    }
}