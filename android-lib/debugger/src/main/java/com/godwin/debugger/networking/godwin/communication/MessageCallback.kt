package com.godwin.debugger.networking.godwin.communication

import java.net.Socket

interface MessageCallback {

    fun sendMessage(message: String, socket: Socket)

}