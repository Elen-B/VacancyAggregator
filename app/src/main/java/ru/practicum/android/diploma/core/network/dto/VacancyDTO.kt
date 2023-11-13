package ru.practicum.android.diploma.core.network.dto

import com.google.gson.annotations.SerializedName
import java.util.Currency

data class VacancyDTO(
    val id: String?,
    val name: String?,
    val salary: SalaryDTO?,
    val employer: EmployerDTO?,
    val area: AreaDTO?
)

data class SalaryDTO(
    val from: String?,
    val to: String?,
    val currency: Currency?,
    val gross: Boolean = false
)

data class EmployerDTO(
    val id: String?,
    val name: String?,
    val logo_urls: Map<String, String>?
)

data class AreaDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)
