package ru.practicum.android.diploma.core.network.dto

sealed interface Request {
    data class VacancyDetailsRequest(val id: String): Request
    data class SimilarVacancyRequest(val id: String): Request

    data class AreaTreeRequest(val id: String): Request

    data class VacancySearchRequest(val text: String): Request

    object CountryRequest: Request

    object AreasFullTreeRequest: Request

    data class AreaDataRequest(val id: String): Request
}