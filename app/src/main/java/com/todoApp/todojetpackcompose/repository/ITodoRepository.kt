package com.todoApp.todojetpackcompose.repository

import com.todoApp.todojetpackcompose.models.Todo
import com.todoApp.todojetpackcompose.models.TodoListItem
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {

    suspend fun addTodo(todo:Todo):Flow<Boolean>
    suspend fun getAllTodos():Flow<List<TodoListItem>>
    suspend fun getCompletedTodos():Flow<List<TodoListItem>>
    suspend fun getDeletedTodos():Flow<List<TodoListItem>>
    suspend fun updateTodo(todo:TodoListItem,isDone:Boolean):Flow<Boolean>
    suspend fun deleteTodo(todo:TodoListItem):Flow<Boolean>
    suspend fun getTodoById(todoId:Int):Flow<TodoListItem>
    suspend fun getNotification() :Flow<Int>
}