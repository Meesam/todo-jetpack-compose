package com.todoApp.todojetpackcompose.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoApp.todojetpackcompose.ui.authentication.events.UserRegistraitionEvent
import com.todoApp.todojetpackcompose.util.UiEvent


@Composable
fun UserRegistration(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: UserRegisterViewModel = hiltViewModel()
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
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
    ) {
        Text(text = "Register here", fontSize = 32.sp, fontWeight = FontWeight.W500 )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewModel.userName.value,
            onValueChange = {viewModel.onEvent(UserRegistraitionEvent.onUserNameChange(it))},
            placeholder = {
                Text(text = "Name")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person , contentDescription = "user name")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewModel.email.value,
            onValueChange = {viewModel.onEvent(UserRegistraitionEvent.onEmailChange(it))},
            placeholder = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email , contentDescription = "user name")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = viewModel.password.value,
            onValueChange = {viewModel.onEvent(UserRegistraitionEvent.onPasswordChange(it))},
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Password , contentDescription = "user name")
            },
            visualTransformation = PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.height(20.dp))

        Row {
            OutlinedButton(onClick = {
                viewModel.onEvent(UserRegistraitionEvent.OnRegisterButtonClick(
                    email = viewModel.email.value,
                    password = viewModel.password.value)
                )
            }) {
                Text(text = "Register")
            }

            OutlinedButton(onClick = {
                viewModel.onEvent(UserRegistraitionEvent.OnLoginButtonClick)
            }) {
                Text(text = "Login")
            }
        }

    }
}

/*@Preview
@Composable
fun UserRegistrationPreview(){
    UserRegistration()
}*/