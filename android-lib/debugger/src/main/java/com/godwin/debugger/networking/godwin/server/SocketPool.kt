package com.godwin.debugger.networking.godwin.server

import java.net.Socket
import java.util.*

object SocketPool {
   private var socketList = ArrayList<Socket>()

    fun add(socket: Socket) {
        if (!socketList.contains(socket)) {
            socketList.add(socket)
        }
    }

    fun remove(socket: Socket) {
        socketList.remove(socket)
    }

    fun isContains(socket: Socket): Boolean {
        return socketList.contains(socket)
    }
}