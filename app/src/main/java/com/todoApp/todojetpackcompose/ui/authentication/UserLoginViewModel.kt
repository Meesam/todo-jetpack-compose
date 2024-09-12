package com.todoApp.todojetpackcompose.ui.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.todoApp.todojetpackcompose.ui.authentication.events.UserLoginEvents
import com.todoApp.todojetpackcompose.ui.authentication.events.UserRegistraitionEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserLoginViewModel @Inject constructor() :ViewModel() {

  val email = mutableStateOf("")
  val password = mutableStateOf("")

  fun onEvent(event: UserLoginEvents){
     when(event){
         is UserLoginEvents.onEmailChange ->{
             email.value = event.email
         }
         is UserLoginEvents.onPasswordChange ->{
             password.value = event.password
         }

         is UserLoginEvents.OnLoginButtonClick ->{

         }
     }
  }
}