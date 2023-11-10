package ru.practicum.android.diploma.details.presentation.models

import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

sealed class DetailState {
    object Loading : DetailState()
    class Error(val message: String) : DetailState()
    class Success (val data: ProfessionDetail): DetailState()
}