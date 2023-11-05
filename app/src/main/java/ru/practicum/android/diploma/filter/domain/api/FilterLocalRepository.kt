package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.FilterParameters

interface FilterLocalRepository {
    fun getFilterParameters(): FilterParameters?

    fun saveFilterParameters(filterParameters: FilterParameters)

    fun removeFilterParameters()
}