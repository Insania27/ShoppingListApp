package com.example.shoppinglist.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.ui.theme.GrayLight

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val list = viewModel.colorItemListState.value

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Column(
        Modifier
            .fillMaxSize()
            .background(GrayLight)
            .padding(
                top = topPadding,
                end = 16.dp,
                start = 16.dp,
                bottom = 16.dp
            )
    ) {

        Text("Title color", fontSize = 18.sp, fontWeight = Bold)

        Text("Select title color:", fontSize = 14.sp, color = Color.Gray)

        LazyRow(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)) {
            items(list) { item ->
                UiColorItem(item) { event ->
                    viewModel.onEvent(event)
                }
            }
        }

    }

}