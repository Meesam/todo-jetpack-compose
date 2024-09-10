package com.todoApp.todojetpackcompose.ui.add_edit_todo.events


sealed class AddEditTodoEvent {
   data class onTitleTextChange(val title:String = "") :AddEditTodoEvent()
   data class onDescriptionChange(val description:String = "") :AddEditTodoEvent()
   data class OnSaveTodoClick(val title: String, val description: String) : AddEditTodoEvent()

}