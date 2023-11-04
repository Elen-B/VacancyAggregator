package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.filter.data.dto.AreaResponse
import ru.practicum.android.diploma.filter.data.dto.AreaTreeResponse
import ru.practicum.android.diploma.filter.data.mapper.AreaMapper
import ru.practicum.android.diploma.filter.data.mapper.AreaTreeMapper
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class FilterRepositoryImpl(private val networkClient: NetworkClient) : FilterRepository {
    override suspend fun getCountries(): Resource<List<Area>> {
        val response = networkClient.doRequest(Request.CountryRequest)
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> Resource.Success((response as AreaResponse).results.map { areaDto ->
                AreaMapper.map(
                    areaDto
                )
            })
            else -> Resource.Error("")
        }
    }

    override suspend fun getAreas(id: String): Resource<List<Area>> {
        val response = networkClient.doRequest(Request.AreaRequest(id))
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                val flattenArea = AreaTreeMapper.map((response as AreaTreeResponse).results).sortedBy { it.name }
                Resource.Success(flattenArea)
            }

            else -> Resource.Error("")
        }
    }
}