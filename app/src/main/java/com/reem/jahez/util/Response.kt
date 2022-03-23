package com.reem.jahez.util

sealed class Response <T>(val data: T? = null, val message:String? = null) {
    class Success <T> (data:T): Response<T>(data)
    class Loading <T>(): Response <T> ()
    class Error <T>(message:String): Response<T>(message = message)
}
