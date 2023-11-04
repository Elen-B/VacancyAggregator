package ru.practicum.android.diploma.details.domain.models

data class ProfessionSimillar (
    val employerId: String,
    val employerName:String,
    val employerLogo: String?,
    val id:String,
    val name:String,
    val city: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
)