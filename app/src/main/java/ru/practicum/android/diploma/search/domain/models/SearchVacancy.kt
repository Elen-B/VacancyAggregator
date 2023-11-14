package ru.practicum.android.diploma.search.domain.models

data class SearchVacancy (
    val id: String?,
    val name: String?,
    val salary: Salary?,
    val employer: Employer?,
    val logo: String?
)