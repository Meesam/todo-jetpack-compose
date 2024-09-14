package com.todoApp.todojetpackcompose.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.todoApp.todojetpackcompose.ui.navigation.events.BottomNavigationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor() :ViewModel() {
   private val _selectedIndex = mutableIntStateOf(0)
    val selectedIndex = _selectedIndex

   fun onEvent(events: BottomNavigationEvents){
       when(events){
           is BottomNavigationEvents.onSelectedIndex ->{
               _selectedIndex.intValue = events.index
           }
       }
   }
}