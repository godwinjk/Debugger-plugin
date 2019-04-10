package com.godwin.network.godwin.server

import com.godwin.common.Logger
import com.godwin.network.godwin.communication.MessageContract
import com.godwin.network.godwin.communication.TcpCallbackSubscriber
import com.godwin.network.godwin.util.SharedUidGenerator
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ClientHandler(socket: Socket) : Runnable, MessageContract {


    private val mSocket: Socket = socket
    private var inStream: DataInputStream? = null
    private var outStream: DataOutputStream? = null
    private var isClosed = false
    private val uId = SharedUidGenerator.getUid()
    private var tag: Any? = null

    init {
        try {
            inStream = DataInputStream(mSocket.getInputStream())
            outStream = DataOutputStream(mSocket.getOutputStream())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun run() {
        try {
            var clientMessage: String
            while (!isClosed) {
                clientMessage = inStream!!.readUTF()
                println(clientMessage)

                TcpCallbackSubscriber.publishOnMessage(clientMessage, this)
            }
        } catch (e: Exception) {
            Logger.e(e.message)
            TcpCallbackSubscriber.publishOnClosed(this)
        }
    }

    override fun getTag(): Any? {
        return tag
    }

    override fun setTag(tag: Any?) {
        this.tag = tag
    }

    override fun sendMessage(message: String) {
        if (outStream != null) {
            outStream!!.writeUTF(message)
            outStream!!.flush()
        }
    }

    override fun close() {
        if (inStream != null) {
            inStream!!.close()
        }
        if (outStream != null) {
            outStream!!.close()
        }
        mSocket.close()
        isClosed = true
        TcpCallbackSubscriber.publishOnClosed(this)
    }

    override fun getSocket(): Socket {
        return mSocket
    }

    override fun getUid(): Int {
        return uId
    }
}