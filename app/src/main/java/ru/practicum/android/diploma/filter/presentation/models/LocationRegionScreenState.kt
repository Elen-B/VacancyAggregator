package ru.practicum.android.diploma.filter.presentation.models

import ru.practicum.android.diploma.filter.domain.models.Area

sealed interface LocationRegionScreenState {
    object Error: LocationRegionScreenState

    object Empty: LocationRegionScreenState

    data class Content(val regionList: List<Area>): LocationRegionScreenState
}