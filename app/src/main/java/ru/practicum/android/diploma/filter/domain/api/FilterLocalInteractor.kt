package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.FilterParameters

interface FilterLocalInteractor {
    fun getFilterParameters(): FilterParameters?

    fun saveFilterParameters(filterParameters: FilterParameters?)
}