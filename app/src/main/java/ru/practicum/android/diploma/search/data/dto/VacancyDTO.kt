package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.domain.models.CurrencyType

data class VacancyDTO(
    val id: String?,
    val name: String,
    val salary: SalaryDTO,
    val employer: EmployerDTO,
    val logo_urls: String)

data class SalaryDTO(
    val from: String?,
    val to: String?,
    val currency: CurrencyType?,
    val gross: Boolean = false
)

data class EmployerDTO(
    val id: String?,
    val name: String?
)
