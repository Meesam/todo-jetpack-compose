package com.todoApp.todojetpackcompose.ui.authentication.events

sealed class UserLoginEvents {
    data class onEmailChange(val email: String="") : UserLoginEvents()
    data class onPasswordChange(val password: String = "") : UserLoginEvents()
    data class OnLoginButtonClick(val email: String, val password: String) : UserLoginEvents()
    data object OnNewUserButtonClick : UserLoginEvents()
    data object OnUserSignOutButtonClick : UserLoginEvents()
}