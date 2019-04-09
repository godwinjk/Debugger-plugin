package com.godwin.network.godwin.server

import java.net.Socket

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