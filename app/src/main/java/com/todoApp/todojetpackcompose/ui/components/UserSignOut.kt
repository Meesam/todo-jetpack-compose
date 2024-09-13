package com.todoApp.todojetpackcompose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoApp.todojetpackcompose.ui.authentication.UserLoginViewModel
import com.todoApp.todojetpackcompose.ui.authentication.events.UserLoginEvents
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent

@Composable
fun UserSignOut(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: UserLoginViewModel = hiltViewModel()
){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.White
        ),
        onClick = {
       viewModel.onEvent(UserLoginEvents.OnUserSignOutButtonClick)
        onNavigate(UiEvent.Navigate(Routes.USER_LOGIN))
  }) {
      Icon(imageVector = Icons.Filled.PowerSettingsNew , contentDescription = "LogOut" )
  }
}