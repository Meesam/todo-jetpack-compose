package com.todoApp.todojetpackcompose.repository

import com.todoApp.todojetpackcompose.api.ITodoApi
import com.todoApp.todojetpackcompose.models.Todo
import com.todoApp.todojetpackcompose.models.TodoListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoApi: ITodoApi): ITodoRepository {

    override suspend fun addTodo(todo: Todo): Flow<TodoListItem> = flow {
        emit(todoApi.addNewTodo(todo))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllTodos(): Flow<List<TodoListItem>> = flow{
        emit(todoApi.getAllTodo())
    }.flowOn(Dispatchers.IO)

    override suspend fun getCompletedTodos(): Flow<List<TodoListItem>> = flow {
        emit(todoApi.getAllCompletedTodo())
    }.flowOn(Dispatchers.IO)

    override suspend fun getDeletedTodos(): Flow<List<TodoListItem>> = flow {
        emit(todoApi.getAllDeletedTodo())
    }.flowOn(Dispatchers.IO)

    override suspend fun updateTodo(todo: TodoListItem, isDone:Boolean): Flow<TodoListItem> = flow {
        emit(todoApi.updateTodo(todo.copy(
            isCompleted = isDone
        )))
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteTodo(todo: TodoListItem): Flow<TodoListItem> = flow {
        emit(todoApi.updateTodo(todo.copy(
            isDeleted = true
        )))
    }.flowOn(Dispatchers.IO)

    override suspend fun getTodoById(todoId: Int): Flow<TodoListItem> = flow{
        emit(todoApi.getTodoById(todoId))
    }.flowOn(Dispatchers.IO)


}