package com.rohanbhagat.androidtraining.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.rememberLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rohanbhagat.androidtraining.model.Product
import com.rohanbhagat.androidtraining.model.ProductListingResponse
import com.rohanbhagat.androidtraining.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ListingScreen(controller: NavHostController) {
    val owner = rememberLifecycleOwner()

    val context = LocalContext.current
    val data = remember { mutableStateOf(ProductListingResponse(null, 0, 0, 0)) }

    LaunchedEffect(Unit) {
        owner.lifecycleScope.launch(Dispatchers.IO) {
            val result = RetrofitBuilder.provideApiService()
                .getProducts(100, "title,price,thumbnail,category")


            if (result.products?.isNotEmpty() == true) {
                data.value = result
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    ComposeListingScreen(data.value.products, controller)
}

@Composable
fun ComposeListingScreen(list: List<Product>? = null, controller: NavHostController) {
    if (list == null || list.isEmpty()) return

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Text(
                text = "Products",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 32.dp),
                style = MaterialTheme.typography.displaySmall
            )

            LazyColumn {
                items(list.size) { it ->
                    ItemCard(list[it], controller)
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: Product, controller: NavHostController) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(Color.LightGray),
        onClick = {
            controller.navigate("Details/${item.id}")
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.Fit,
                model = item.thumbnail,
                contentDescription = "Image"
            )

            Column {
                Text(
                    text = item.title,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )

                Text(
                    text = item.price.toString(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                )
            }
        }
    }
}