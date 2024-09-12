package com.todoApp.todojetpackcompose.ui.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoApp.todojetpackcompose.models.Todo
import com.todoApp.todojetpackcompose.ui.add_edit_todo.events.AddEditTodoEvent
import com.todoApp.todojetpackcompose.ui.authentication.events.UserRegistraitionEvent
import com.todoApp.todojetpackcompose.util.ApiState
import com.todoApp.todojetpackcompose.util.UiEvent
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class UserRegisterViewModel @Inject constructor() :ViewModel() {
  val userName = mutableStateOf("")
  val email = mutableStateOf("")
  val password = mutableStateOf("")

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

            }
        }
    }


}