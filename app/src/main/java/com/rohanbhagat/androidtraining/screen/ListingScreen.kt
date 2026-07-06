package com.rohanbhagat.androidtraining.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rohanbhagat.androidtraining.model.Product
import com.rohanbhagat.androidtraining.network.NetworkStatus
import com.rohanbhagat.androidtraining.network.RetrofitBuilder
import kotlinx.coroutines.launch

private sealed interface ListingUiState {
    data object Loading : ListingUiState
    data class Success(val products: List<Product>) : ListingUiState
    data class Error(val isOffline: Boolean) : ListingUiState
}

@Composable
fun ListingScreen(controller: NavHostController, networkStatus: NetworkStatus) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var uiState by remember { mutableStateOf<ListingUiState>(ListingUiState.Loading) }

    suspend fun loadProducts() {
        uiState = ListingUiState.Loading
        try {
            val result = RetrofitBuilder.provideApiService()
                .getProducts(100, "title,price,thumbnail,category")

            uiState = if (result.products?.isNotEmpty() == true) {
                ListingUiState.Success(result.products)
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                ListingUiState.Error(isOffline = false)
            }
        } catch (_: Exception) {
            uiState = ListingUiState.Error(isOffline = !networkStatus.isConnected)
        }
    }

    LaunchedEffect(Unit) {
        loadProducts()
    }

    // If nothing has loaded yet because we were offline, retry as soon as connectivity returns.
    LaunchedEffect(networkStatus.isConnected) {
        if (networkStatus.isConnected && uiState is ListingUiState.Error) {
            loadProducts()
        }
    }

    when (val state = uiState) {
        is ListingUiState.Loading -> LoadingScreen()

        is ListingUiState.Error -> NoticeScreen(
            icon = if (state.isOffline) Icons.Filled.WifiOff else Icons.Filled.ErrorOutline,
            title = if (state.isOffline) "No Internet Connection" else "Something Went Wrong",
            message = if (state.isOffline) {
                "You're offline. Please check your connection and try again."
            } else {
                "We couldn't load the products. Please try again."
            },
            onRetry = { scope.launch { loadProducts() } }
        )

        is ListingUiState.Success -> ComposeListingScreen(state.products, controller)
    }
}

@Composable
private fun LoadingScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun NoticeScreen(
    icon: ImageVector,
    title: String,
    message: String,
    onRetry: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = Color.Gray
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = message,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 14.sp
            )
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("Retry")
            }
        }
    }
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
