package com.rohanbhagat.androidtraining.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class ConnectionType {
    WIFI, CELLULAR, ETHERNET, VPN, NONE
}

@Parcelize
data class NetworkStatus(
    val connectionType: ConnectionType = ConnectionType.NONE,
    val isConnected: Boolean = false,
    val latencyMs: Long? = null
) : Parcelable
