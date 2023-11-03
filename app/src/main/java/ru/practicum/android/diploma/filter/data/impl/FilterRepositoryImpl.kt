package ru.practicum.android.diploma.filter.data.impl

import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.filter.data.dto.AreaResponse
import ru.practicum.android.diploma.filter.data.mapper.AreaMapper
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
            Response.RESULT_NETWORK_ERROR -> Resource.Error("Подключение отсутствует")
            Response.RESULT_BAD_REQUEST -> Resource.Error("Ошибка сервера")
            else -> Resource.Error("Неизвестная ошибка")
        }
    }
}