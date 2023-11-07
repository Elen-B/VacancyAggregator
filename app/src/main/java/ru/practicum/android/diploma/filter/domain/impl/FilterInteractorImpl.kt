package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class FilterInteractorImpl(private val repository: FilterRepository): FilterInteractor {
    override suspend fun getCountries(): Pair<List<Area>?, String?> {
        return when(val res = repository.getCountries()) {
            is Resource.Success -> Pair(res.data, null)
            is Resource.Error -> Pair(null, res.message)
        }
    }

    override suspend fun getAreas(id: String): Pair<List<Area>?, String?> {
        val res = if (id.isEmpty()) repository.getAreas() else repository.getAreas(id)
        return when(res) {
            is Resource.Success -> Pair(res.data, null)
            is Resource.Error -> Pair(null, res.message)
        }
    }

    override suspend fun getCountryByRegion(id: String): Area? {
        var parent = when (val region = repository.getArea(id)) {
            is Resource.Success -> region.data
            is Resource.Error -> null
        }

        while (parent != null && !parent.parentId.isNullOrEmpty()) {
            val res = parent.parentId?.let { repository.getArea(it) }
            parent = when (res) {
                is Resource.Success -> res.data
                is Resource.Error -> null
                else -> null
            }
        }
        return parent
    }

    override suspend fun getIndustries(): Pair<List<Industry>?, String?> {
        return when(val res = repository.getIndustries()) {
            is Resource.Success -> Pair(res.data, null)
            is Resource.Error -> Pair(null, res.message)
        }
    }
}