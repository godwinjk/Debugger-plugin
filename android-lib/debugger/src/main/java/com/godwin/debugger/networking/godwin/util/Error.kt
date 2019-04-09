package com.godwin.debugger.networking.godwin.util


class Error {
    var code: Int? = null
    var message: String? = null

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    fun getErrorCode(): Int {
        return code!!
    }

    fun getErrorMessage(): String {
        if (message == null) {
            var message = ""
            val clazz = ErrorCode::class.java
            val fields = clazz.fields

            try {
                for (errCodeField in fields) {
                    if (code === errCodeField.getInt(clazz)) {
                        message = errCodeField.getAnnotation(ErrorDescription::class.java).value
                        break
                    }
                }
            } catch (ignored: IllegalAccessException) {
                return "Something wen't wrong"
            } catch (ignored: NullPointerException) {
                return "Something wen't wrong"
            }

            return message
        }
        return message!!
    }
}