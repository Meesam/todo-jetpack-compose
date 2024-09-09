package com.todoApp.todojetpackcompose.api

import com.todoApp.todojetpackcompose.models.TodoListItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ITodoApi {

    @GET("getAllTodos")
    suspend fun getAllTodo():Response<List<TodoListItem>>

    @GET("getAllCompletedTodos")
    suspend fun getAllCompletedTodo():Response<List<TodoListItem>>

    @GET("getAllDeletedTodos")
    suspend fun getAllDeletedTodo():Response<List<TodoListItem>>

    @GET("getTodoById/{todoId}")
    suspend fun getTodoById(todoId:Int):Response<TodoListItem>

    @POST("addNew")
    suspend fun addNewTodo(@Body todo:TodoListItem):Response<Boolean>

    @POST("updateTodo")
    suspend fun updateTodo(@Body todo:TodoListItem):Response<Boolean>


}