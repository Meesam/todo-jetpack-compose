package com.todoApp.todojetpackcompose.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todoApp.todojetpackcompose.ui.authentication.events.UserLoginEvents

@Composable
fun UserLogin(
    viewModel: UserLoginViewModel = hiltViewModel()
){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(text = "Login here", fontSize = 32.sp, fontWeight = FontWeight.W500 )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = viewModel.email.value,
            onValueChange = {
                viewModel.onEvent(UserLoginEvents.onEmailChange(it))
            },
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
            onValueChange = {
                viewModel.onEvent(UserLoginEvents.onPasswordChange(it))
            },
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Password , contentDescription = "user name")
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "Login")
        }
    }
}

@Preview
@Composable
fun UserLoginPreview(){
    UserLogin()
}