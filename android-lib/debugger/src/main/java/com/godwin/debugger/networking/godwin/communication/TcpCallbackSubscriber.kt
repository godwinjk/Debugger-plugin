package com.godwin.debugger.networking.godwin.communication

import java.util.*

/**
 * Created by Godwin on 3/4/2019 5:09 PM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2017
 */
object TcpCallbackSubscriber {
    private val callbackList = ArrayList<Any>()

    fun subscribe(any: CommunicationListener) {
        if (!callbackList.contains(any)) {
            callbackList.add(any)
        }
    }

    fun unSubscribe(any: CommunicationListener) {
        callbackList.remove(any)
    }

    fun publishOnConnected(socket: MessageContract) {
        for (any in callbackList) {
            if (any is CommunicationListener) {
                any.onClientConnected(socket)
            }
        }
    }

    fun publishOnClosed(socket: MessageContract) {
        for (any in callbackList) {
            if (any is CommunicationListener) {
                any.onClientDisconnected(socket,null)
            }
        }
    }

    fun publishOnMessage(message: String, socket: MessageContract) {
        for (any in callbackList) {
            if (any is CommunicationListener) {
                any.onMessage(message, socket)
            }
        }
    }
}