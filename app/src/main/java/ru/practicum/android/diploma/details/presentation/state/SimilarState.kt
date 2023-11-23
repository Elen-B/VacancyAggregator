package ru.practicum.android.diploma.details.presentation.state

import ru.practicum.android.diploma.core.domain.models.Vacancy

sealed interface SimilarState {
    object Loading : SimilarState
    object Empty : SimilarState
    data class Error(
        val message: String,
        val errorImagePath: Int
    ) : SimilarState

    data class Success(val data: List<Vacancy>) : SimilarState
}