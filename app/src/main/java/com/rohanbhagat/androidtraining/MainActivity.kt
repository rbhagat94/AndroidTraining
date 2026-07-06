package com.rohanbhagat.androidtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rohanbhagat.androidtraining.network.NetworkStatus
import com.rohanbhagat.androidtraining.ui.component.NetworkStatusBar
import com.rohanbhagat.androidtraining.ui.theme.AndroidTrainingTheme
import com.rohanbhagat.androidtraining.viewmodel.NetworkViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTrainingTheme {
                val controller = rememberNavController()
                val networkViewModel: NetworkViewModel = viewModel()

                // Restores the last known network status across process death so the bar
                // shows a correct value immediately, before the ViewModel re-observes it.
                var lastKnownStatus by rememberSaveable { mutableStateOf(NetworkStatus()) }
                LaunchedEffect(Unit) { networkViewModel.seed(lastKnownStatus) }

                val networkStatus by networkViewModel.networkStatus.collectAsState()
                LaunchedEffect(networkStatus) { lastKnownStatus = networkStatus }

                Column(modifier = Modifier.fillMaxSize()) {
                    NetworkStatusBar(status = networkStatus)
                    NavigationController(controller, networkStatus)
                }
            }
        }
    }
}
