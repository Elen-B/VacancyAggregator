package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("id")
    val id: String,
    @SerializedName("level")
    val level: Level,
    @SerializedName("name")
    val name: String
)