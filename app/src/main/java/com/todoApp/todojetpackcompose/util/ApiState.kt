package com.todoApp.todojetpackcompose.util

sealed class ApiState<out T> {
    data class Success<out T>(val data:T):ApiState<T>()
    data class Failure<Nothing>(val errorMessage :String):ApiState<Nothing>()
    object Loading:ApiState<Nothing>()
}