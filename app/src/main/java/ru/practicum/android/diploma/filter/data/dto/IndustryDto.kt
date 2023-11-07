package ru.practicum.android.diploma.filter.data.dto

import com.google.gson.annotations.SerializedName

data class IndustryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)