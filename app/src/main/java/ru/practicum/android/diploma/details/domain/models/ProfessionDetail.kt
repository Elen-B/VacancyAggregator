package ru.practicum.android.diploma.details.domain.models

data class ProfessionDetail(
    val id: Int,
    val name:String,
    val employmentId: Int?,
    val employmentName: String?,
    val employerId:Int,
    val employerName:String,
    val employerLogo: String,
    val experienceId: String?,
    val experienceName: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?
)
