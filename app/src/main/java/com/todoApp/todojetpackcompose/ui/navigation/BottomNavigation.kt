package com.todoApp.todojetpackcompose.ui.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.todoApp.todojetpackcompose.util.Routes
import com.todoApp.todojetpackcompose.util.UiEvent

@Composable
fun BottomNavigation(
    onNavigate:(UiEvent.Navigate)->Unit,
    notificationCount:Int
){
    val navItems = listOf(
        NavItem(title = "All Todo", Icons.Default.Task),
        NavItem(title = "Completed", Icons.Default.TaskAlt),
        NavItem(title = "Deleted", Icons.Default.DeleteSweep)
    )
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Log.d("selectedIndex" , "$selectedIndex")

    fun onItemClickHandler(index:Int, navItem:NavItem){
        selectedIndex = index
        when(navItem.title){
            "All Todo" -> {
                onNavigate(UiEvent.Navigate(Routes.TODO_LIST))
            }
            "Completed" -> {
                onNavigate(UiEvent.Navigate(Routes.ALL_COMPLETED_TODO_LIST))
            }
            "Deleted" -> {
                onNavigate(UiEvent.Navigate(Routes.ALL_DELETED_TODO_LIST))
            }
        }
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor =  Color.White,
        tonalElevation = 5.dp,

    ) {
        navItems.forEachIndexed{ index, navItem->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {onItemClickHandler(index, navItem)},
                icon = {
                    if(navItem.title =="All Todo" && notificationCount > 0){
                        BadgedBox(badge = {
                            Badge(
                                contentColor = Color.Red,
                                ){
                                    Text(text =  notificationCount.toString(), color = Color.White)
                            }
                        }) {
                            navItem.icon?.let { Icon(imageVector = it, contentDescription = "Icon") }
                        }
                    }else{
                        navItem.icon?.let { Icon(imageVector = it, contentDescription = "Icon") }
                    }
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}