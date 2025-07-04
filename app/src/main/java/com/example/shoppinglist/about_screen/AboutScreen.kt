package com.example.shoppinglist.about_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.ui.theme.BlueGreen
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.GreenLight


@Preview(showBackground = true)
@Composable
fun AboutScreen() {

    val uriHandler = LocalUriHandler.current

    Column(
        Modifier.fillMaxSize().background(GrayLight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp),
            tint = BlueGreen
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "This app was developed by Insania27 \n" +
                    "Version - 1.2.0 \n" +
                    "To get more information \n",
            textAlign = TextAlign.Center
        )

        Text(
            ">>> Click here <<<",
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://github.com/Insania27")
                },
            color = MaterialTheme.colorScheme.primary
        )
    }

}