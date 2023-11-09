package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry

interface FilterInteractor {
    suspend fun getCountries(): Pair<List<Area>?, String?>

    suspend fun getAreas(id: String): Pair<List<Area>?, String?>

    suspend fun getCountryByRegion(id: String): Area?

    suspend fun getIndustries(): Pair<List<Industry>?, String?>
}