package com.todoApp.todojetpackcompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    val todos: StateFlow<List<TodoListItem>>
        get() = repository.todos

    init {
        viewModelScope.launch{
            repository.getAllTodos()
        }
    }
}