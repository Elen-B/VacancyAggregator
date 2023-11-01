package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("original")
    val original: String,
    @SerializedName("240")
    val x240: String,
    @SerializedName("90")
    val x90: String
)