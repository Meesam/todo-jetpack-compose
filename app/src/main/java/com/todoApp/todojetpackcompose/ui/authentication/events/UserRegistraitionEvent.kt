package com.todoApp.todojetpackcompose.ui.authentication.events


sealed class UserRegistraitionEvent {
    data class onUserNameChange(val userName:String = "") : UserRegistraitionEvent()
    data class onEmailChange(val email: String="") : UserRegistraitionEvent()
    data class onPasswordChange(val password: String = "") : UserRegistraitionEvent()
    data class OnRegisterButtonClick(val email: String, val password: String) : UserRegistraitionEvent()
    data object OnLoginButtonClick :UserRegistraitionEvent()
}