package com.example.shoppinglist.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.R
import com.example.shoppinglist.dialog.MainDialog
import com.example.shoppinglist.navigation.NavigationGraph
import com.example.shoppinglist.utils.Routes
import com.example.shoppinglist.utils.UiEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavHostController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UiEvent.NavigateMain -> {
                    mainNavHostController.navigate(uiEvent.route)
                }
                is UiEvent.Navigate -> {
                    navController.navigate(uiEvent.route)
                }
                else -> {  }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNav(currentRoute = currentRoute){ route ->
                viewModel.onEvent(MainScreenEvent.Navigate(route))
            }
        },
        floatingActionButton = {
            if (viewModel.showFloatingButton.value) FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MainScreenEvent.OnNewItemClick(currentRoute!!))
                },
                containerColor = Color(android.graphics.Color.parseColor(viewModel.color.value)),
                shape = CircleShape
            ) { Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
    )
    {
        NavigationGraph(navController){ route ->
            viewModel.onEvent(MainScreenEvent.NavigateMain(route))
        }
        MainDialog(dialogController = viewModel)
    }
}