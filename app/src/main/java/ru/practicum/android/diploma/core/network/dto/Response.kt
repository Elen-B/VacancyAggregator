package ru.practicum.android.diploma.core.network.dto

open class Response() {
    var resultCode = 0

    companion object {
        const val RESULT_SUCCESS = 200
        const val RESULT_BAD_REQUEST = 400
        const val RESULT_NETWORK_ERROR = -1
        const val RESULT_UNKNOWN_REQUEST = 400
    }
}