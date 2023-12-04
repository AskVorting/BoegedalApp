package com.example.boegedalapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun aboutScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Whoe are we?",
            fontSize = 35.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Bøgedal Brew is located in Vejle river valley. We brew handcrafted beer from start to finish. The beer flows from vat to vat using a hoist without the use of pumps. The beer is flavorful, full-bodied, elegant and balanced, with many layers of flavor like in a good glass of wine. We brew in a specially designed freefall brewery (all gravity). The herb is boiled over a wood fire.\n" +
                    "\n" +
                    "Gitte Holmboe and Casper Vorting have created history at Bøgedal by reinterpreting the old farm with out-of-date buildings into a small utopia.",
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(8.dp)
        )
    }
}