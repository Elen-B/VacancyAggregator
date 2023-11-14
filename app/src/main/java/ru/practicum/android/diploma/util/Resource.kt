package ru.practicum.android.diploma.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorImagePath: Int? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, errorImagePath: Int? = null) :
        Resource<T>(data, message, errorImagePath)
}