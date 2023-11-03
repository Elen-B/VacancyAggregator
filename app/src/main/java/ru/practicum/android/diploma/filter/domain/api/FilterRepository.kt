package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.util.Resource

interface FilterRepository {

    suspend fun getCountries(): Resource<List<Area>>
}