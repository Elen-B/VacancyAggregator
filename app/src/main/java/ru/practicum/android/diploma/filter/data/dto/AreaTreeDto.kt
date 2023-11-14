package ru.practicum.android.diploma.filter.data.dto

import com.google.gson.annotations.SerializedName

data class AreaTreeDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("parent_id")
    val parentId: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("areas")
    val areas: List<AreaTreeDto>?
)