package ru.practicum.android.diploma.filter.presentation.models

import ru.practicum.android.diploma.core.domain.models.Area

sealed interface LocationCountryScreenState {
    object Error: LocationCountryScreenState

    data class Content(val countryList: List<Area>): LocationCountryScreenState
}