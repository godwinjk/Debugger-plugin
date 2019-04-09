package com.godwin.debugger.networking.godwin.util

@Target (AnnotationTarget.FIELD,AnnotationTarget.TYPE)
@Retention ( AnnotationRetention.RUNTIME)

annotation class ErrorDescription(
    /**
     * Value string.
     *
     * @return the string
     */
    val value: String
)