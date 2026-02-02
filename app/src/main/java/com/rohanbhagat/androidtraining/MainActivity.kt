package com.rohanbhagat.androidtraining

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.rohanbhagat.androidtraining.ui.theme.AndroidTrainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("MainActivity", "KONRAD")
        setContent {
            AndroidTrainingTheme {
                val controller = rememberNavController()

                NavigationController(controller)
            }
        }
    }
}