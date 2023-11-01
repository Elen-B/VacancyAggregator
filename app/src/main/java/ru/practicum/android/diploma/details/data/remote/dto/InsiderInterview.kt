package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class InsiderInterview(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)