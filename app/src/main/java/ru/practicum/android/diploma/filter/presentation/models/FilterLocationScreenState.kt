package ru.practicum.android.diploma.filter.presentation.models

import ru.practicum.android.diploma.core.domain.models.Area

sealed interface FilterLocationScreenState {
    data class Content(val country: Area?, val region: Area?): FilterLocationScreenState
}