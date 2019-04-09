package com.godwin.debugger.networking.godwin.client

import com.godwin.debugger.networking.godwin.communication.MessageContract
import com.godwin.debugger.networking.godwin.communication.TcpCallbackSubscriber
import com.godwin.network.godwin.worker.ThreadPoolProvider
import com.godwin.debugger.networking.godwin.util.SharedUidGenerator
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ClientManager : MessageContract {

    private var mSocket: Socket? = null
    private var inStream: DataInputStream? = null
    private var outStream: DataOutputStream? = null
    private var isClosed = false
    private val uId = SharedUidGenerator.getUid()
    private var tag: Any? = null

    private val runnable = Runnable {
        try {
            var clientMessage: String
            while (!isClosed) {
                clientMessage = inStream!!.readUTF()
//                println(clientMessage)

                TcpCallbackSubscriber.publishOnMessage(clientMessage, this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            TcpCallbackSubscriber.publishOnClosed(this)
        }
    }

    fun connect(ip: String, port: Int) {
        try {
            mSocket = Socket(ip, port)

            inStream = DataInputStream(mSocket!!.getInputStream())
            outStream = DataOutputStream(mSocket!!.getOutputStream())

            ThreadPoolProvider.executeBackGroundTask(runnable)

        } catch (e: Exception) {
            e.printStackTrace()
            mSocket = null
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
        mSocket!!.close()
        isClosed = true
        TcpCallbackSubscriber.publishOnClosed(this)
    }

    override fun getSocket(): Socket? {
        return mSocket
    }

    override fun getUid(): Int {
        return uId
    }
}