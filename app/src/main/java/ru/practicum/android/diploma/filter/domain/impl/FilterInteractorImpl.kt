package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class FilterInteractorImpl(private val repository: FilterRepository): FilterInteractor {
    override suspend fun getCountries(): Pair<List<Area>?, String?> {
        return when(val res = repository.getCountries()) {
            is Resource.Success -> Pair(res.data, null)
            is Resource.Error -> Pair(null, res.message)
        }
    }

    override suspend fun getAreas(id: String): Pair<List<Area>?, String?> {
        val res = repository.getAreas(id)
        return when(res) {
            is Resource.Success -> Pair(res.data, null)
            is Resource.Error -> Pair(null, res.message)
        }
    }
}