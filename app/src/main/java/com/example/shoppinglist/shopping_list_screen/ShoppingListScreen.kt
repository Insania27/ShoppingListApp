package com.example.shoppinglist.shopping_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import com.example.shoppinglist.dialog.MainDialog
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.Red
import com.example.shoppinglist.utils.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    val itemsList = viewModel.list.collectAsState(initial = emptyList())

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UiEvent.Navigate -> {
                    onNavigate(uiEvent.route)
                }
                is UiEvent.ShowSnackBar -> {
                    val result = snackbarHost.showSnackbar(
                        message = uiEvent.message,
                        actionLabel = "Undone",
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(ShoppingListEvent.OnUndoneDeleteList)
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHost) { data ->
                Snackbar(
                    modifier = Modifier.padding(bottom = 160.dp, start = 20.dp, end = 20.dp),
                    containerColor = Red,
                    contentColor = Color.White,
                    action = {
                        data.visuals.actionLabel?.let { actionLabel ->
                            TextButton(onClick = { data.performAction() }) {
                                Text(actionLabel, color = Color.White)
                            }
                        }
                    }
                ) {
                    Text(data.visuals.message)
                }
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight)
                .padding(top = topPadding),
            contentPadding = PaddingValues(bottom = 110.dp)
        ) {
            items(itemsList.value) { item ->
                UiShoppingListItem(item) { event ->
                    viewModel.onEvent(event)
                }
            }
        }

        MainDialog(viewModel)

        if (itemsList.value.isEmpty()) {
            Text(
                text = "Empty",
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                color = Color(android.graphics.Color.parseColor(viewModel.color.value))
            )
        }
    }

}