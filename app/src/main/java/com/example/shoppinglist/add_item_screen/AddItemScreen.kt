package com.example.shoppinglist.add_item_screen

import android.annotation.SuppressLint
import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.dialog.MainDialog
import com.example.shoppinglist.ui.theme.BlueGreen
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.Red
import com.example.shoppinglist.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val snackbarHost = remember { SnackbarHostState() }

    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UiEvent.ShowSnackBar -> {
                    snackbarHost.showSnackbar(
                        message = uiEvent.message
                    )
                }
                else -> {

                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHost) { data ->
                Snackbar(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight)
        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topPadding)
                    .padding(5.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(
                        modifier = Modifier.weight(1f),
                        value = viewModel.itemText.value,
                        onValueChange = { text ->
                            viewModel.onEvent(AddItemEvent.OnTextChange(text))
                        },
                        label = { Text("New item", fontSize = 12.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            disabledTextColor = DarkText,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = DarkText
                        ),
                        singleLine = true
                    )

                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddItemEvent.OnItemSave)
                        },
                        modifier = Modifier.background(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_icon),
                            contentDescription = "Add",
                            tint = Color(android.graphics.Color.parseColor(viewModel.color.value))
                        )
                    }

                }

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 3.dp,
                        start = 5.dp,
                        end = 5.dp
                    )
            ) {
                if (itemsList != null) {
                    items(itemsList.value) { item ->
                        UiAddItem(
                            item = item,
                            onEvent = { event ->
                                viewModel.onEvent(event)
                            }
                        )
                    }
                }
            }
        }

        MainDialog(dialogController = viewModel)

        if (itemsList?.value?.isEmpty() == true){
            Text(
                text = "Empty",
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }

}