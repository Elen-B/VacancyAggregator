package ru.practicum.android.diploma.details.data.remote


import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)