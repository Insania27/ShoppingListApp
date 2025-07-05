package com.example.shoppinglist.new_note_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.Red
import com.example.shoppinglist.utils.UiEvent
import kotlin.math.sin
import androidx.core.graphics.toColorInt


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {

    val snackbarHost = remember { SnackbarHostState() }

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowSnackBar -> {
                    snackbarHost.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
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

        Box(
            Modifier
                .fillMaxSize()
                .background(GrayLight)
                .padding(top = topPadding)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp)
            ) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {

                        TextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.title,
                            onValueChange = { text ->
                                viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
                            },
                            label = {
                                Text("Title", fontSize = 14.sp)
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color(viewModel.color.value.toColorInt()),
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        )

                        IconButton(
                            onClick = {
                                viewModel.onEvent(NewNoteEvent.OnSave)
                            }
                        )
                        {

                            Icon(
                                painter = painterResource(R.drawable.save_icon),
                                contentDescription = "Save",
                                tint = Color(viewModel.color.value.toColorInt()),

                                )

                        }

                    }

                }

                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = viewModel.description,
                    onValueChange = { text ->
                        viewModel.onEvent(NewNoteEvent.OnDescriptionChange(text))
                    },
                    label = {
                        Text(
                            "Description",
                            fontSize = 14.sp,
                            style = TextStyle(color = MaterialTheme.colorScheme.primary),
                            color = Color(viewModel.color.value.toColorInt())
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = DarkText
                    )
                )

            }

        }
    }

}