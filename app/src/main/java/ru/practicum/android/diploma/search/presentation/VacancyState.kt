package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.core.domain.models.Vacancy

sealed interface VacancyState {

    object Loading : VacancyState

    data class Content(
        val vacancy: List<Vacancy>,
        val count: String,
        val lastPage: Boolean = false
    ) : VacancyState

    data class Update(
        val vacancy: List<Vacancy>,
        val count: String,
        val lastPage: Boolean = false
    ) : VacancyState

    data class VacancyError(
        val errorMessage: String
    ) : VacancyState

    data class ServerError(
        val errorMessage: String
    ) : VacancyState


    data class Empty(
        val message: String
    ) : VacancyState

}