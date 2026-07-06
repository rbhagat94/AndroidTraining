package com.rohanbhagat.androidtraining.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rohanbhagat.androidtraining.network.ConnectivityObserver
import com.rohanbhagat.androidtraining.network.LatencyMonitor
import com.rohanbhagat.androidtraining.network.NetworkStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val connectivityObserver = ConnectivityObserver(application)

    private val _networkStatus = MutableStateFlow(NetworkStatus())
    val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()

    private var latencyJob: Job? = null
    private var hasObservedConnectivity = false

    init {
        viewModelScope.launch {
            connectivityObserver.observe().collect { state ->
                hasObservedConnectivity = true
                _networkStatus.update {
                    it.copy(
                        connectionType = state.connectionType,
                        isConnected = state.isConnected,
                        latencyMs = if (state.isConnected) it.latencyMs else null
                    )
                }
                restartLatencyPolling(state.isConnected)
            }
        }
    }

    /** Seeds the last known status before real connectivity data arrives (e.g. after process death). */
    fun seed(status: NetworkStatus) {
        if (!hasObservedConnectivity) _networkStatus.value = status
    }

    private fun restartLatencyPolling(isConnected: Boolean) {
        latencyJob?.cancel()
        if (!isConnected) return

        latencyJob = viewModelScope.launch {
            while (true) {
                val latency = LatencyMonitor.measure()
                _networkStatus.update { it.copy(latencyMs = latency) }
                delay(5000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        latencyJob?.cancel()
    }
}
