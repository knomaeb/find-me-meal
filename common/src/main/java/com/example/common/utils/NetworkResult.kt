package com.example.common.utils

sealed class NetworkResult<T>(
    val data: T? = null, val message: String? = null
) {
    class success<T>(data: T?) : NetworkResult<T>(data)

    class error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class loading<T> : NetworkResult<T>()

}