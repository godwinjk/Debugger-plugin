package com.godwin.network.godwin.util

object SharedUidGenerator {
    private var clientUid: Int = 0

    fun getUid(): Int {
        return clientUid++
    }
}