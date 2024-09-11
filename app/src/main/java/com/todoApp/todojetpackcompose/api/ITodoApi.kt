package com.todoApp.todojetpackcompose.api

import com.todoApp.todojetpackcompose.models.Todo
import com.todoApp.todojetpackcompose.models.TodoListItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ITodoApi {

    @GET("getAllTodos")
    suspend fun getAllTodo():List<TodoListItem>

    @GET("getAllCompletedTodos")
    suspend fun getAllCompletedTodo():List<TodoListItem>

    @GET("getAllDeletedTodos")
    suspend fun getAllDeletedTodo():List<TodoListItem>

    @GET("getTodoById/{todoId}")
    suspend fun getTodoById(todoId:Int):TodoListItem

    @POST("addNew")
    suspend fun addNewTodo(@Body todo:Todo):Boolean

    @POST("updateTodo")
    suspend fun updateTodo(@Body todo:TodoListItem):Boolean


}