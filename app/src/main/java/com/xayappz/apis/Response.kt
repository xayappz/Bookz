package com.xayappz.apis

sealed class Response<T>(val data: T? = null, val error: String? = null) {
    class Loading<T> : Response<T>()

    class Success<T>(dataSuccess: T? = null) : Response<T>(data = dataSuccess)

    class Error<T>(errorMsg: String) : Response<T>(error = errorMsg)

}
