package com.todoApp.todojetpackcompose.repository

import android.util.Log
import com.todoApp.todojetpackcompose.api.ITodoApi
import com.todoApp.todojetpackcompose.models.TodoListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoApi: ITodoApi) {

    private val _todos = MutableStateFlow<List<TodoListItem>>(emptyList())
    val todos:StateFlow<List<TodoListItem>>
        get() = _todos

    suspend fun getAllTodos(){
        try {
            val response = todoApi.getAllTodo()
            if(response.isSuccessful && response.body() != null){
                _todos.emit(response.body()!!)
            }
        }catch (ex:Exception){
            Log.d("Todo Error", ex.message.toString())
        }
    }

}