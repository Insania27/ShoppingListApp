package com.example.shoppinglist.settings_screen

import androidx.compose.ui.graphics.Color
import com.example.shoppinglist.ui.theme.BlueGreen
import com.example.shoppinglist.ui.theme.GreenLight
import com.example.shoppinglist.ui.theme.GreenLow
import com.example.shoppinglist.ui.theme.OrangeHigh
import com.example.shoppinglist.ui.theme.OrangeLow
import com.example.shoppinglist.ui.theme.Red


object ColorUtils {

    val colorList = listOf(
        "#003FFF",
        "#D500FF",
        "#00D4FF",
        "#FF6200EE",
        "#FF0000",
        "#FFDE00",
        "#FF8400",
        "#267E61",
    )

    fun getProgressColor(progress: Float): Color {
        return when(progress){
            in 0.0..0.199 -> Red
            in 0.2..0.399 -> OrangeLow
            in 0.4..0.599 -> OrangeHigh
            in 0.6..0.799 -> GreenLow
            in 0.8..1.0 -> BlueGreen
            else -> Red
        }
    }

}