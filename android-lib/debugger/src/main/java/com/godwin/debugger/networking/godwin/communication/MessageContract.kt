package com.godwin.debugger.networking.godwin.communication

import java.net.Socket

interface MessageContract {
    fun sendMessage(message: String)
    fun getSocket(): Socket?
    fun close()
    fun getUid(): Int

    fun getTag(): Any?
    fun setTag(tag: Any?)
}