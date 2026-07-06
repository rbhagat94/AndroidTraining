package com.rohanbhagat.androidtraining.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

object LatencyMonitor {
    suspend fun measure(
        host: String = "8.8.8.8",
        port: Int = 53,
        timeoutMs: Int = 1500
    ): Long? = withContext(Dispatchers.IO) {
        runCatching {
            Socket().use { socket ->
                val start = System.currentTimeMillis()
                socket.connect(InetSocketAddress(host, port), timeoutMs)
                System.currentTimeMillis() - start
            }
        }.getOrNull()
    }
}
