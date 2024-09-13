package com.todoApp.todojetpackcompose.ui.authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.todoApp.todojetpackcompose.models.Todo
import com.todoApp.todojetpackcompose.ui.add_edit_todo.events.AddEditTodoEvent
import com.todoApp.todojetpackcompose.ui.authentication.events.UserRegistraitionEvent
import com.todoApp.todojetpackcompose.util.ApiState
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class UserRegisterViewModel @Inject constructor() :ViewModel() {
  val userName = mutableStateOf("")
  val email = mutableStateOf("")
  val password = mutableStateOf("")
  private val _uiEvent = Channel<UiEvent> ()
  val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: UserRegistraitionEvent){
        when (event){
            is UserRegistraitionEvent.onUserNameChange -> {
                userName.value = event.userName
            }
            is UserRegistraitionEvent.onEmailChange -> {
                email.value = event.email
            }

            is UserRegistraitionEvent.onPasswordChange -> {
                password.value = event.password
            }

            is UserRegistraitionEvent.OnRegisterButtonClick -> {
                createUserInFireBase(event.email, event.password)
            }

            is UserRegistraitionEvent.OnLoginButtonClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.USER_LOGIN))
            }
        }
    }

    private fun createUserInFireBase(email:String = "", password:String = ""){
       FirebaseAuth.getInstance()
           .createUserWithEmailAndPassword(email, password)
           .addOnSuccessListener {
               sendUiEvent(UiEvent.Navigate(Routes.USER_LOGIN))
           }
           .addOnFailureListener{
               Log.d("Auth ${it.localizedMessage}" , "" )
           }
    }
    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}