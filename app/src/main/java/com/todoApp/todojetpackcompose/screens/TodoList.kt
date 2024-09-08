package com.todoApp.todojetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.viewmodels.TodosViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun TodoListScreen(){
  val todoViewModel:TodosViewModel = viewModel()
  val todos = todoViewModel.todos.collectAsState()
  LazyColumn(modifier = Modifier.padding(top = 50.dp)) {
      items(todos.value){
          TodoListItemScreen(it)
   }
  }
}

@Composable
fun TodoListItemScreen(todo:TodoListItem){
  Column(modifier = Modifier
      .fillMaxSize()
      .padding(10.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(Color.LightGray)
      .padding(10.dp)
  ) {
      Text(text = todo.title)
      Spacer(modifier = Modifier.height(10.dp))
      Text(text = todo.description)
  }
}