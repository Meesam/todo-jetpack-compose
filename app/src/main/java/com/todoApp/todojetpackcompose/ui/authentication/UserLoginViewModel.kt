package com.todoApp.todojetpackcompose.ui.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.todoApp.todojetpackcompose.ui.authentication.events.UserLoginEvents
import com.todoApp.todojetpackcompose.ui.authentication.events.UserRegistraitionEvent
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserLoginViewModel @Inject constructor() :ViewModel() {

    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

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
             signInUser(event.email,event.password)
         }

         is UserLoginEvents.OnNewUserButtonClick ->{
             sendUiEvent(UiEvent.Navigate(Routes.USER_REGISTRATION))
         }

         is UserLoginEvents.OnUserSignOutButtonClick ->{
             signOutUser()
         }
     }
  }

    private fun signInUser(email:String = "", password:String ="" ){
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
            }
            .addOnFailureListener{ ex ->
                ex.localizedMessage?.let { ex -> Log.d("Sign is", ex) }
            }
    }

    private fun signOutUser(){
        FirebaseAuth.getInstance()
            .signOut()
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}