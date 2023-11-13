package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.ParamData

interface FilterInteractor {
    suspend fun getCountries(): ParamData<List<Area>?>

    suspend fun getAreas(id: String): ParamData<List<Area>?>

    suspend fun getCountryByRegion(id: String): Area?

    suspend fun getIndustries(): ParamData<List<Industry>?>
}