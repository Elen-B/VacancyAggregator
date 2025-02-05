package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface FilterRepository {

    suspend fun getCountries(): Resource<List<Area>>

    suspend fun getAreas(id: String): Resource<List<Area>>

    suspend fun getAreas(): Resource<List<Area>>

    suspend fun getArea(id: String): Resource<Area>

    suspend fun getIndustries(): Resource<List<Industry>>
}