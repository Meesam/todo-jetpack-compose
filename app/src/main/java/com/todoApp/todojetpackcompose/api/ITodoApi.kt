package com.todoApp.todojetpackcompose.api

import com.todoApp.todojetpackcompose.models.TodoListItem
import retrofit2.Response
import retrofit2.http.GET

interface ITodoApi {

    @GET("getAllTodos")
    suspend fun getAllTodo():Response<List<TodoListItem>>
}