package com.todoApp.todojetpackcompose.ui.todo_list.events

import com.todoApp.todojetpackcompose.models.TodoListItem

sealed class TodoListEvent {
  data class OnDeleteTodoClick(val todo:TodoListItem): TodoListEvent()
  data class OnDoneChange(val todo:TodoListItem, val isDone:Boolean) : TodoListEvent()
  data object OnAddTodoClick : TodoListEvent()
  data class OnTodoClick(val todo:TodoListItem) : TodoListEvent()
  object GetNoteEvent:TodoListEvent()
}