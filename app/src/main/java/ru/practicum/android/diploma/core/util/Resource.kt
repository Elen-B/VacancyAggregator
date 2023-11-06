package ru.practicum.android.diploma.core.util

sealed class Resource<T>(
    val data: T? = null,
    val errorInfo: ErrorInfo? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(errorInfo: ErrorInfo, data: T? = null) :
        Resource<T>(data, errorInfo)
}
