package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val message: String? = null, val errorImagePath: Int? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, errorImagePath: Int, data: T? = null): Resource<T>(data, message, errorImagePath)
}