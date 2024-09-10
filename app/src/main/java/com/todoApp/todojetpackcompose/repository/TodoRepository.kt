package com.todoApp.todojetpackcompose.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.todoApp.todojetpackcompose.api.ITodoApi
import com.todoApp.todojetpackcompose.models.TodoListItem
import com.todoApp.todojetpackcompose.ui.domain.repository.ITodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoApi: ITodoApi):ITodoRepository {

    override suspend fun addTodo(todo: TodoListItem): Flow<TodoListItem> = flow {
        emit(todoApi.addNewTodo(todo))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllTodos(): Flow<List<TodoListItem>> = flow{
        emit(todoApi.getAllTodo())
    }

    override suspend fun getCompletedTodos(): Flow<List<TodoListItem>> = flow {
        emit(todoApi.getAllCompletedTodo())
    }

    override suspend fun getDeletedTodos(): Flow<List<TodoListItem>> = flow {
        emit(todoApi.getAllDeletedTodo())
    }

    override suspend fun updateTodo(todo: TodoListItem, isDone:Boolean): Flow<TodoListItem> = flow {
        emit(todoApi.updateTodo(todo.copy(
            isCompleted = isDone
        )))
    }

    override suspend fun deleteTodo(todo: TodoListItem): Flow<TodoListItem> = flow {
        emit(todoApi.updateTodo(todo.copy(
            isDeleted = true
        )))
    }

    override suspend fun getTodoById(todoId: Int): Flow<TodoListItem> = flow{
        emit(todoApi.getTodoById(todoId))
    }


}