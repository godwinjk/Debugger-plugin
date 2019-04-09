package com.godwin.network.godwin.communication

import java.net.Socket

interface MessageCallback {

    fun sendMessage(message: String, socket: Socket)

}