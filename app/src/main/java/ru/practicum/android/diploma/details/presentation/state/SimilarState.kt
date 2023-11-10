package ru.practicum.android.diploma.details.presentation.state

import ru.practicum.android.diploma.core.domain.models.Vacancy

sealed class SimilarState {
    object Loading : SimilarState()
    class Error(
        val message: String,
        val errorImagePath: Int) : SimilarState()
    class Success (val data: List<Vacancy>): SimilarState()
}