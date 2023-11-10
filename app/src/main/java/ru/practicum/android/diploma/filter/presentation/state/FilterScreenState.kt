package ru.practicum.android.diploma.filter.presentation.state

import ru.practicum.android.diploma.filter.domain.models.FilterParameters

sealed interface FilterScreenState {
    data class Started(val data: FilterParameters, val update: Boolean) : FilterScreenState

    data class Modified(val data: FilterParameters, val update: Boolean) : FilterScreenState
}