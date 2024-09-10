package com.todoApp.todojetpackcompose.ui.domain.repository

import com.todoApp.todojetpackcompose.models.TodoListItem
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {

    suspend fun addTodo(todo:TodoListItem):Flow<TodoListItem>
    suspend fun getAllTodos():Flow<List<TodoListItem>>
    suspend fun getCompletedTodos():Flow<List<TodoListItem>>
    suspend fun getDeletedTodos():Flow<List<TodoListItem>>
    suspend fun updateTodo(todo:TodoListItem,isDone:Boolean):Flow<TodoListItem>
    suspend fun deleteTodo(todo:TodoListItem):Flow<TodoListItem>
    suspend fun getTodoById(todoId:Int):Flow<TodoListItem>
}