package com.rohanbhagat.androidtraining.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(productId: Int) {
    Log.d("DetailsScreen", "productId: $productId")
    Text(text = "This is detail screen", modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp))
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(1)
}