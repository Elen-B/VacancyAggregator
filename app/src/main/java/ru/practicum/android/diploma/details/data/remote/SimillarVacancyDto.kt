package ru.practicum.android.diploma.details.data.remote

import com.google.gson.annotations.SerializedName

data class SimillarVacancyDto(
    @SerializedName("employer")
    val employer:Employer,
    @SerializedName("id")
    val id:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("area")
    val area:Area?,
    @SerializedName("salary")
    val salary:Salary?,
)
