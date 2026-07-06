package com.rohanbhagat.androidtraining.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rohanbhagat.androidtraining.model.Product
import com.rohanbhagat.androidtraining.network.RetrofitBuilder
import okio.ArrayIndexOutOfBoundsException

@Composable
fun DetailsScreen(productId: Int, controller: NavHostController?) {
    if (productId == 2) throw ArrayIndexOutOfBoundsException()

    if (productId == 3) {
        Thread.sleep(6000)
    }

    val context = LocalContext.current

    val productState = remember { mutableStateOf<Product?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(productId) {
        try {
            val result = RetrofitBuilder.provideApiService().getProductById(productId)
            productState.value = result
            isLoading.value = false
        } catch (_: Exception) {
            Toast.makeText(context, "Failed to load product", Toast.LENGTH_SHORT).show()
            isLoading.value = false
        }
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "Buy Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))

            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = productState.value?.thumbnail,
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
                IconButton(
                    onClick = {
                        controller?.popBackStack()
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 30.dp)
                        .align(Alignment.TopStart)
                        .background(Color.White, CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Black"
                    )
                }
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 30.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.White, CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Black"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productState.value?.title ?: "",
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = ("$" + productState.value?.price.toString()) ?: "",
                    color = Color(0xFF6750A4),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(48.dp)

                )
                Text(
                    text = productState.value?.rating.toString(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 5.dp)

                )
                Text(
                    text = "( ${productState.value?.reviews?.size.toString()} Review )",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 12.dp)

                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 20.dp),

                ) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${productState.value?.description}",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .padding(bottom = innerPadding.calculateBottomPadding())

            ) {
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                productState.value?.reviews?.forEach { it ->
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.width(60.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Reviewer Icon",
                                tint = Color(0xFF6750A4),
                                modifier = Modifier.size(40.dp)
                            )

                            Text(
                                text = it.reviewerName,
                                fontSize = 10.sp
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f)
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFC107),
                                    modifier = Modifier.size(30.dp)
                                )

                                Text(
                                    text = "${it.rating}",
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                )
                            }

                            Text(text = it.comment)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderComponent(thumbnail: String, controller: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = thumbnail,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        )
        IconButton(
            onClick = {
                controller.popBackStack()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 30.dp)
                .align(Alignment.TopStart)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Black"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(1, controller = null)
}