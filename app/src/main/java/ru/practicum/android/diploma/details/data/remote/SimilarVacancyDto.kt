package ru.practicum.android.diploma.details.data.remote

import com.google.gson.annotations.SerializedName

data class SimilarVacancyDto(
    @SerializedName("items")
    val items: List<ItemSimilar>
)
