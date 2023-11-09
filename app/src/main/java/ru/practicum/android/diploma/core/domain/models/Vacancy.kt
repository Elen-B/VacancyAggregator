package ru.practicum.android.diploma.core.domain.models

data class Vacancy(
    val id: String?,
    val name: String?,
    val area: Area?,
    val salary: Salary?,
    val employer: Employer?
)
