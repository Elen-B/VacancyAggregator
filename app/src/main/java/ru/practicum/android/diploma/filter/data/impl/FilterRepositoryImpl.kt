package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.filter.data.dto.AreaListTreeResponse
import ru.practicum.android.diploma.filter.data.dto.AreaTreeResponse
import ru.practicum.android.diploma.filter.data.dto.IndustryListTreeResponse
import ru.practicum.android.diploma.filter.data.mapper.AreaMapper
import ru.practicum.android.diploma.filter.data.mapper.AreaTreeMapper
import ru.practicum.android.diploma.filter.data.mapper.IndustryTreeMapper
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class FilterRepositoryImpl(private val networkClient: NetworkClient) : FilterRepository {
    override suspend fun getCountries(): Resource<List<Area>> {
        val response = networkClient.doRequest(Request.CountryRequest)
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> Resource.Success((response as AreaListTreeResponse).results.map { areaTreeDto ->
                AreaMapper.map(
                    areaTreeDto
                )
            }.sortedBy { it.id == OTHER_COUNTRIES_ID })
            else -> Resource.Error("")
        }
    }

    override suspend fun getAreas(id: String): Resource<List<Area>> {
        val response = networkClient.doRequest(Request.AreaTreeRequest(id))
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                val flattenArea = AreaTreeMapper.map((response as AreaTreeResponse).results).sortedBy { it.name }
                Resource.Success(flattenArea)
            }

            else -> Resource.Error("")
        }
    }

    override suspend fun getAreas(): Resource<List<Area>> {
        val response = networkClient.doRequest(Request.AreasFullTreeRequest)
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                val flattenArea =
                    ((response as AreaListTreeResponse).results.flatMap { areaTreeDto ->
                        AreaTreeMapper.map(areaTreeDto)
                    }).sortedBy { it.name }
                Resource.Success(flattenArea)
            }

            else -> Resource.Error("")
        }
    }

    override suspend fun getArea(id: String): Resource<Area> {
        val response = networkClient.doRequest(Request.AreaTreeRequest(id))
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                val area =
                    if ((response as AreaTreeResponse).results != null) AreaMapper.map((response).results!!)
                    else null

                Resource.Success(area)
            }

            else -> Resource.Error("")
        }
    }

    override suspend fun getIndustries(): Resource<List<Industry>> {
        val response = networkClient.doRequest(Request.IndustryTreeRequest)
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                val flattenIndustry =
                    ((response as IndustryListTreeResponse).results.flatMap { industryTreeDto ->
                        IndustryTreeMapper.map(industryTreeDto)
                    }).sortedBy { it.name }
                Resource.Success(flattenIndustry)
            }

            else -> Resource.Error("")
        }
    }

    companion object {
        private const val OTHER_COUNTRIES_ID = "1001"
    }
}