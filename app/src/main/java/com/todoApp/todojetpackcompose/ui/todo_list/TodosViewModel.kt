package com.todoApp.todojetpackcompose.ui.todo_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.repository.ITodoRepository
import com.todoApp.todojetpackcompose.ui.todo_list.events.TodoListEvent
import com.todoApp.todojetpackcompose.ui.todo_list.state.TodoStates
import com.todoApp.todojetpackcompose.util.ApiState
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(private val repository: ITodoRepository) : ViewModel() {

    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _updateTodoEventFlow = MutableSharedFlow<ApiState<Boolean>>()
    val updateTodoEventFlow = _updateTodoEventFlow.asSharedFlow()

    private val _deleteTodoEventFlow = MutableSharedFlow<ApiState<Boolean>>()
    val deleteTodoEventFlow = _deleteTodoEventFlow.asSharedFlow()


    private val _getTodoEventFlow = mutableStateOf(TodoStates())
    val getTodoEventFlow:State<TodoStates> = _getTodoEventFlow

    private val _getCompletedTodoEventFlow = mutableStateOf(TodoStates())
    val getCompletedTodoEventFlow:State<TodoStates> = _getCompletedTodoEventFlow

    private val _getdeletedTodoEventFlow = mutableStateOf(TodoStates())
    val getdeletedTodoEventFlow:State<TodoStates> = _getdeletedTodoEventFlow

    private val _getNotificationEventFlow = mutableIntStateOf(0)
    val getNotificationEventFlow = _getNotificationEventFlow

    init {
        viewModelScope.launch {
            repository.getNotification()
                .collect{
                    _getNotificationEventFlow.intValue = it
                }
        }
    }

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
                    repository.deleteTodo(event.todo)
                        .onStart {
                            _deleteTodoEventFlow.emit(ApiState.Loading)
                        }.catch {
                            _deleteTodoEventFlow.emit(ApiState.Failure(errorMessage = "Something went wrong"))
                        }.collect{
                            sendUiEvent(UiEvent.ShowSnackBar("Todo deleted successfully", null))
                            _deleteTodoEventFlow.emit(ApiState.Success(true))
                        }
                }
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.updateTodo(event.todo, event.isDone)
                        .onStart {
                            _updateTodoEventFlow.emit(ApiState.Loading)
                        }.catch {
                            _updateTodoEventFlow.emit(ApiState.Failure(errorMessage = "Something went wrong"))
                        }.collect{
                            _updateTodoEventFlow.emit(ApiState.Success(data = true))
                        }
                }
            }

            TodoListEvent.GetNoteEvent -> {
                viewModelScope.launch {
                    repository.getAllTodos()
                        .onStart {
                            _getTodoEventFlow.value =TodoStates(
                                isLoading = true
                            )
                        }.catch {
                            _getTodoEventFlow.value =
                                TodoStates(
                                    error = "Something went wrong"
                                )
                        }.collect{
                            _getTodoEventFlow.value =
                                TodoStates(
                                    data = it
                                )
                        }
                }
            }

            TodoListEvent.GetComopletedNoteEvent -> {
                viewModelScope.launch {
                    repository.getCompletedTodos()
                        .onStart {
                            _getCompletedTodoEventFlow.value =TodoStates(
                                isLoading = true
                            )
                        }.catch {
                            _getCompletedTodoEventFlow.value =
                                TodoStates(
                                    error = "Something went wrong"
                                )
                        }.collect{
                            _getCompletedTodoEventFlow.value =
                                TodoStates(
                                    data = it
                                )
                        }
                }
            }
            TodoListEvent.GetDeleteNoteEvent -> {
                viewModelScope.launch {
                    repository.getDeletedTodos()
                        .onStart {
                            _getdeletedTodoEventFlow.value =TodoStates(
                                isLoading = true
                            )
                        }.catch {
                            _getdeletedTodoEventFlow.value =
                                TodoStates(
                                    error = "Something went wrong"
                                )
                        }.collect{
                            _getdeletedTodoEventFlow.value =
                                TodoStates(
                                    data = it
                                )
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