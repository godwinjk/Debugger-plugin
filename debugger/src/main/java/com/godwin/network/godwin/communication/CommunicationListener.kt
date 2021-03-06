package com.godwin.network.godwin.communication

import com.godwin.network.godwin.util.Error

interface CommunicationListener {

    fun onClientConnected(socket: MessageContract)

    fun onMessage(message: String, socket: MessageContract)

    fun onClientDisconnected(socket: MessageContract, error: Error?)
}