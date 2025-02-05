package ru.practicum.android.diploma.filter.presentation.state

import ru.practicum.android.diploma.core.domain.models.Area

sealed interface LocationRegionScreenState {
    object Error: LocationRegionScreenState

    object Empty: LocationRegionScreenState

    data class Content(val regionList: List<Area>): LocationRegionScreenState
}