package com.todoApp.todojetpackcompose.ui.add_edit_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoApp.todojetpackcompose.models.Todo
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.ui.add_edit_todo.events.AddEditTodoEvent
import com.todoApp.todojetpackcompose.repository.ITodoRepository
import com.todoApp.todojetpackcompose.util.ApiState
import com.todoApp.todojetpackcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(private val repository: ITodoRepository):ViewModel() {
    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _addTodoEventFlow = MutableSharedFlow<ApiState<Boolean>>()
    val addTodoEventFlow = _addTodoEventFlow.asSharedFlow()

    val title = mutableStateOf("")
    val description = mutableStateOf("")

    fun onEvent(event: AddEditTodoEvent){
        when (event){
            is AddEditTodoEvent.onTitleTextChange -> {
                title.value = event.title
            }
            is AddEditTodoEvent.onDescriptionChange -> {
               description.value = event.description
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    repository.addTodo(
                        Todo(title = title.value, description = description.value)
                    )
                        .onStart {
                            _addTodoEventFlow.emit(ApiState.Loading)
                        }.catch {
                            _addTodoEventFlow.emit(ApiState.Failure(errorMessage = "Something went wrong"))
                        }.collect{
                            sendUiEvent(UiEvent.ShowSnackBar("Todo added successfully", null))
                            _addTodoEventFlow.emit(ApiState.Success(true))
                        }
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