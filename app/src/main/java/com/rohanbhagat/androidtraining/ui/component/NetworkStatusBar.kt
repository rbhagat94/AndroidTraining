package com.rohanbhagat.androidtraining.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohanbhagat.androidtraining.network.ConnectionType
import com.rohanbhagat.androidtraining.network.NetworkStatus

@Composable
fun NetworkStatusBar(status: NetworkStatus, modifier: Modifier = Modifier) {
    val backgroundColor = if (status.isConnected) Color(0xFF2E7D32) else Color(0xFFC62828)

    val label = when {
        !status.isConnected -> "Offline"
        status.connectionType == ConnectionType.WIFI -> "Wi-Fi"
        status.connectionType == ConnectionType.CELLULAR -> "Mobile Data"
        status.connectionType == ConnectionType.ETHERNET -> "Ethernet"
        status.connectionType == ConnectionType.VPN -> "VPN"
        else -> "Connected"
    }

    val latencyText = when {
        !status.isConnected -> null
        status.latencyMs != null -> "${status.latencyMs} ms"
        else -> "measuring…"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        if (latencyText != null) {
            Text(text = " • $latencyText", color = Color.White, fontSize = 12.sp)
        }
    }
}
