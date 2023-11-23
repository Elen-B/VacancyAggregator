package ru.practicum.android.diploma.details.presentation.state

import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

sealed interface DetailState {
    object Loading : DetailState
    data class Error(
        val message: String,
        val errorImagePath: Int
    ) : DetailState

    data class Success(val data: ProfessionDetail, val fromDb: Boolean = false) : DetailState
}