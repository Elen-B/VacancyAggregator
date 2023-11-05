package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Area

interface FilterInteractor {
    suspend fun getCountries(): Pair<List<Area>?, Int?>
}