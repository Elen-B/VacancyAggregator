package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Industry

interface FilterInteractor {
    suspend fun getCountries(): Pair<List<Area>?, String?>

    suspend fun getAreas(id: String): Pair<List<Area>?, String?>

    suspend fun getCuntryByRegion(id: String): Area?

    suspend fun getIndustries(): Pair<List<Industry>?, String?>
}