package ru.practicum.android.diploma.core.data.network.dto

sealed interface Request {
    data class VacancyDetailsRequest(val id: String): Request
    data class SimilarVacancyRequest(val id: String): Request

    data class AreaTreeRequest(val id: String): Request

    data class VacancySearchRequest(val option: HashMap<String,String>): Request

    object CountryRequest: Request

    object AreasFullTreeRequest: Request

    object IndustryTreeRequest: Request
}