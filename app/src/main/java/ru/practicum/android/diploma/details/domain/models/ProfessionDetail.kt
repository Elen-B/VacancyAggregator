package ru.practicum.android.diploma.details.domain.models

import ru.practicum.android.diploma.core.domain.models.Employer
import ru.practicum.android.diploma.core.domain.models.Employment
import ru.practicum.android.diploma.core.domain.models.Experience
import ru.practicum.android.diploma.core.domain.models.Salary

data class ProfessionDetail(
    val id: String,
    val name:String,
    val employment: Employment?,
    val employer: Employer?,
    val address:String?,
    val experience: Experience?,
    val salary: Salary?,
    val description: String?,
    val keySkills: String?,
    val phone: String?,
    val contactName: String?,
    val email: String?,
    val comment:String?,
    val url:String?
)
