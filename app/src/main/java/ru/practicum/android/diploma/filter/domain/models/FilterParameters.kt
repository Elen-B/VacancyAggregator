package ru.practicum.android.diploma.filter.domain.models

data class FilterParameters(
    val country: Area?,
    val region: Area?,
    val industry: Industry?,
    val salary: Int,
    val fSalaryRequired: Boolean = false
)
