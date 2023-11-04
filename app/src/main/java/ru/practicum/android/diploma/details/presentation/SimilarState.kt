package ru.practicum.android.diploma.details.presentation

import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar

sealed class SimilarState {
    object Loading : SimilarState()
    class Error(val message: String) : SimilarState()
    class Success (val data: List<ProfessionSimillar>): SimilarState()
}