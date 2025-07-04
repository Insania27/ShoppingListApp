package com.example.shoppinglist.main_screen

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun BottomNav(
    viewModel: MainScreenViewModel = hiltViewModel(),
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val listItems = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.NoteItem,
        BottomNavItem.AboutItem,
        BottomNavItem.SettingsItem,
    )

    NavigationBar(containerColor = Color.White) {
        listItems.forEach {

            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                    onNavigate(it.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(it.iconId),
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(it.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(android.graphics.Color.parseColor(viewModel.color.value)),
                    selectedTextColor = Color(android.graphics.Color.parseColor(viewModel.color.value)),
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                ),
                alwaysShowLabel = false
            )
        }
    }

}