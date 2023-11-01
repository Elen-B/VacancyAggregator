package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.search.domain.models.SearchVacancy

sealed interface VacancyState {

    object Loading : VacancyState

    data class Content(
        val vacancy: List<SearchVacancy>
    ) : VacancyState

    data class Error(
        val errorMessage: String
    ) : VacancyState

    data class Empty(
        val message: String
    ) : VacancyState

}