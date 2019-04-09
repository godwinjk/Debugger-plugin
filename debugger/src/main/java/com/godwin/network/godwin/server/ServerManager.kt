package com.godwin.network.godwin.server

import com.godwin.network.godwin.communication.TcpCallbackSubscriber
import com.godwin.network.godwin.worker.ThreadPoolProvider
import java.net.ServerSocket

object ServerManager {

    private var serverSocket: ServerSocket? = null

    private val isRunning: Boolean = true

    fun startServer(port: Int) {
        ThreadPoolProvider.executeBackGroundTask(Runnable {
            if (null == serverSocket) {
                serverSocket = ServerSocket(port)
            }
            serverSocket!!.reuseAddress = true
            startListeningForClient()
        })

    }

    private fun startListeningForClient() {
        while (isRunning) {
            val socket = serverSocket!!.accept()

            val handler = ClientHandler(socket)
            SocketPool.add(socket)

            ThreadPoolProvider.executeBackGroundTask(handler)

            TcpCallbackSubscriber.publishOnConnected(handler)
        }
    }
}
