package com.example.shoppinglist.note_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.ui.theme.GreenLight
import com.example.shoppinglist.ui.theme.LightText
import com.example.shoppinglist.ui.theme.Red
import com.example.shoppinglist.utils.Routes


@Composable
fun UiNoteItem(
    viewModel: NoteListViewModel = hiltViewModel(),
    item: NoteItem,
    onEvent: (NoteListEvent) -> Unit,
) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 3.dp, start = 5.dp, end = 5.dp)
        .clickable {
            onEvent(NoteListEvent.OnItemClick(
                Routes.NEW_NOTE + "/${item.id}"
            ))
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {

            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp)
                        .weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(android.graphics.Color.parseColor(viewModel.titleColor.value))
                )

                Text(
                    text = item.time,
                    modifier = Modifier.padding(top = 10.dp, end = 10.dp),
                    color = LightText,
                    fontSize = 14.sp
                )

            }

            Row(Modifier.fillMaxWidth()) {

                Text(
                    text = item.description,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
                        .weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = LightText
                )

                IconButton(
                    onClick = {
                        onEvent(NoteListEvent.OnShowDeleteDialog(item))
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Red
                    )

                }
            }
        }
    }

}