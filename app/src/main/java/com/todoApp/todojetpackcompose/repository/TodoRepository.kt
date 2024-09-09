package com.todoApp.todojetpackcompose.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _completedTodos = MutableStateFlow<List<TodoListItem>>(emptyList())
    val completedTodos:StateFlow<List<TodoListItem>>
        get() = _completedTodos

    suspend fun getAllCompletedTodos(){
        try {
            val response = todoApi.getAllCompletedTodo()
            if(response.isSuccessful && response.body() != null){
                _todos.emit(response.body()!!)
            }
        }catch (ex:Exception){
            Log.d("Todo Error", ex.message.toString())
        }
    }

    private val _deletedTodos = MutableStateFlow<List<TodoListItem>>(emptyList())
    val deletedTodos:StateFlow<List<TodoListItem>>
        get() = _deletedTodos

    suspend fun getAllDeletedTodos(){
        try {
            val response = todoApi.getAllDeletedTodo()
            if(response.isSuccessful && response.body() != null){
                _todos.emit(response.body()!!)
            }
        }catch (ex:Exception){
            Log.d("Todo Error", ex.message.toString())
        }
    }

    private val _isAddDone = mutableStateOf<Boolean>(false)
    val isDone:State<Boolean>
        get() = _isAddDone

    suspend fun addNewTodo(todo:TodoListItem){
        try{
            val response = todoApi.addNewTodo(todo)
            if(response.isSuccessful && response.body() != null){
                _isAddDone.value = response.body()!!
            }
        }catch (ex:Exception){
            Log.d("Todo Error", ex.message.toString())
        }
    }

    private val _isUpdateDone = mutableStateOf<Boolean>(false)
    val isUpdateDone:State<Boolean>
        get() = _isUpdateDone

    suspend fun updateTodo(todo:TodoListItem){
        try{
            Log.d("Todo Input", todo.toString())
            val response = todoApi.updateTodo(todo)
            if(response.isSuccessful && response.body() != null){
                _isUpdateDone.value = response.body()!!
            }
        }catch (ex:Exception){
            Log.d("Todo Error", ex.message.toString())
        }
    }

    private val _isDeleteDone = mutableStateOf<Boolean>(false)
    val isDeleteDone:State<Boolean>
        get() = _isDeleteDone

    suspend fun deleteTodo(todo:TodoListItem){
        try{
            val response = todoApi.updateTodo(todo)
            if(response.isSuccessful && response.body() != null){
                _isDeleteDone.value = response.body()!!
            }
        }catch (ex:Exception){
            Log.d("Todo Error", ex.message.toString())
        }
    }
}