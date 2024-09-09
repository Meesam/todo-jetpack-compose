package com.todoApp.todojetpackcompose.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.repository.TodoRepository
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoListEvent){
        when (event){
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO +"?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnDeleteTodoClick -> {
               viewModelScope.launch {
                   repository.deleteTodo(event.todo.copy(
                       isDeleted = true
                   ))
                   sendUiEvent(UiEvent.ShowSnackBar(message = "Todo deleted successfully", null))
               }
            }
            is TodoListEvent.OnDoneChange -> {
               viewModelScope.launch {
                   repository.updateTodo(event.todo.copy(
                       isCompleted = true
                   ))
               }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}