package com.todoApp.todojetpackcompose.models

data class TodoListItem(
    val id:Int,
    val title:String,
    val description:String,
    val isCompleted:Boolean,
    val isDeleted:Boolean,
    val createdDate:String,
    var lastModifiedDate:String?
)
