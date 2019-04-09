package com.godwin.network.godwin.communication

import java.net.Socket

interface MessageContract {
    fun sendMessage(message: String)
    fun getSocket(): Socket
    fun close()
    fun getUid(): Int

    fun getTag(): Any?
    fun setTag(tag: Any?)
}