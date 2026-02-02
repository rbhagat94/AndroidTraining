package com.rohanbhagat.androidtraining

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rohanbhagat.androidtraining.screen.DetailsScreen
import com.rohanbhagat.androidtraining.screen.ListingScreen

@Composable
fun NavigationController(controller: NavHostController) {
    NavHost(navController = controller, startDestination = "Listing") {
        composable("Listing") {
            ListingScreen(controller)
        }

        composable(
            route = "Details/{productId}",
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType // Specify the type
                nullable = false
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId", 1) ?: 1
            DetailsScreen(productId)
        }
    }
}