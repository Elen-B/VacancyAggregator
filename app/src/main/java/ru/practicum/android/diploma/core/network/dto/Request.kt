package ru.practicum.android.diploma.core.network.dto

sealed interface Request {
    data class VacancyDetailsRequest(val id: String): Request

    data class AreaRequest(val areaId: String): Request

    object CountryRequest: Request
}