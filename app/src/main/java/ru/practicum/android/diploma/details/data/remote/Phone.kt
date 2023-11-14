package ru.practicum.android.diploma.details.data.remote

import com.google.gson.annotations.SerializedName

data class Phone(
    @SerializedName("city")
    val city: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("country")
    val country: String,
    @SerializedName("number")
    val number: String,
)
