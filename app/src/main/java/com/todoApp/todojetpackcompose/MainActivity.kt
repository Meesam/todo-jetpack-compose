package com.todoApp.todojetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todoApp.todojetpackcompose.ui.add_edit_todo.AddEditTodoScreen
import com.todoApp.todojetpackcompose.ui.authentication.UserLogin
import com.todoApp.todojetpackcompose.ui.authentication.UserRegistration
import com.todoApp.todojetpackcompose.ui.theme.TodoJetpackComposeTheme
import com.todoApp.todojetpackcompose.ui.todo_list.AllCompletedTodoList
import com.todoApp.todojetpackcompose.ui.todo_list.AllDeletedTodoList
import com.todoApp.todojetpackcompose.ui.todo_list.AllTodoList
import com.todoApp.todojetpackcompose.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoJetpackComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.USER_LOGIN){
                    composable(Routes.USER_REGISTRATION){
                        UserRegistration()
                    }

                    composable(Routes.USER_LOGIN){
                        UserLogin()
                    }

                    composable(Routes.TODO_LIST){
                        AllTodoList(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            type = "all-todos"
                        )
                    }
                    composable(Routes.ALL_COMPLETED_TODO_LIST){
                        AllCompletedTodoList(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            type = "all-completed-todos"
                        )
                    }
                    composable(Routes.ALL_DELETED_TODO_LIST){
                        AllDeletedTodoList(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            type = "all-deleted-todos"
                        )
                    }
                    composable(Routes.ADD_EDIT_TODO){
                        AddEditTodoScreen(
                            onPopBackStack = { navController.popBackStack()}
                        )
                    }
                }
            }
        }
    }
}


