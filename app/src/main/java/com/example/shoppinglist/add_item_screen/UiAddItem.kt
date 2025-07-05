package com.example.shoppinglist.add_item_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.data.AddItem
import com.example.shoppinglist.ui.theme.BlueGreen
import com.example.shoppinglist.ui.theme.GreenLight
import com.example.shoppinglist.ui.theme.Red
import androidx.core.graphics.toColorInt


@Composable
fun UiAddItem(
    item: AddItem,
    onEvent: (AddItemEvent) -> Unit,
    viewModel: AddItemViewModel = hiltViewModel()
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                onEvent(AddItemEvent.OnShowEditDialog(item))
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { isChecked ->
                    onEvent(AddItemEvent.OnCheckedChange(item.copy(isChecked = isChecked)))
                },
                colors = CheckboxColors(
                    checkedCheckmarkColor = Color.White,
                    uncheckedCheckmarkColor = Color.White,
                    checkedBoxColor = Color(viewModel.color.value.toColorInt()),
                    uncheckedBoxColor = Color.White,
                    disabledCheckedBoxColor = Color.White,
                    disabledUncheckedBoxColor = Color.White,
                    disabledIndeterminateBoxColor = Color.White,
                    checkedBorderColor = Color(viewModel.color.value.toColorInt()),
                    uncheckedBorderColor = Color.Gray,
                    disabledBorderColor = Color.Gray,
                    disabledUncheckedBorderColor = Color.Gray,
                    disabledIndeterminateBorderColor = Color.Gray,
                )
            )

            Text(
                modifier = Modifier.weight(1f),
                text = item.name,
                fontSize = 12.sp
            )

            IconButton(
                onClick = {
                    onEvent(AddItemEvent.OnDelete(item))
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete_icon),
                    contentDescription = "Delete",
                    tint = Red
                )
            }
        }
    }

}