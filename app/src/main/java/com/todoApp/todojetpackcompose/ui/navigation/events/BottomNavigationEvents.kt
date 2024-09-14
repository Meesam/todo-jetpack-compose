package com.todoApp.todojetpackcompose.ui.navigation.events

sealed class BottomNavigationEvents {
  data class onSelectedIndex(val index:Int = 0) : BottomNavigationEvents()
}