package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.FilterSaveMode

interface FilterLocalInteractor {
    fun getFilterParameters(): FilterParameters?

    fun saveFilterParameters(
        filterParameters: FilterParameters?,
        saveMode: FilterSaveMode? = FilterSaveMode.ALL
    )
}