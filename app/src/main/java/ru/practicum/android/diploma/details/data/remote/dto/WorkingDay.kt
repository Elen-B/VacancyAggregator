package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class WorkingDay(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)