package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.ParamData
import ru.practicum.android.diploma.util.Resource

class FilterInteractorImpl(private val repository: FilterRepository): FilterInteractor {
    override suspend fun getCountries(): ParamData<List<Area>?> {
        return when(val res = repository.getCountries()) {
            is Resource.Success -> ParamData(res.data)
            is Resource.Error -> ParamData(isError = true)
        }
    }

    override suspend fun getAreas(id: String): ParamData<List<Area>?> {
        val res = if (id.isEmpty()) repository.getAreas() else repository.getAreas(id)
        return when(res) {
            is Resource.Success -> ParamData(res.data)
            is Resource.Error -> ParamData(isError = true)
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

    override suspend fun getIndustries(): ParamData<List<Industry>?> {
        return when(val res = repository.getIndustries()) {
            is Resource.Success -> ParamData(res.data)
            is Resource.Error -> ParamData(isError = true)
        }
    }
}