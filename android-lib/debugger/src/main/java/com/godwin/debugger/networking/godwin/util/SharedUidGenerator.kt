package com.godwin.debugger.networking.godwin.util

object SharedUidGenerator {
    private var clientUid: Int = 0

    fun getUid(): Int {
        return clientUid++
    }
}