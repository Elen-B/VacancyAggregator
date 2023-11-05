package ru.practicum.android.diploma.core.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.core.network.utils.networkAvailable
import ru.practicum.android.diploma.details.data.DetailVacancyResponse
import ru.practicum.android.diploma.details.data.SimilarVacancyResponse
import ru.practicum.android.diploma.filter.data.dto.AreaDataResponse
import ru.practicum.android.diploma.filter.data.dto.AreaListTreeResponse
import ru.practicum.android.diploma.filter.data.dto.AreaResponse
import ru.practicum.android.diploma.filter.data.dto.AreaTreeResponse
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse


class RetrofitNetworkClient(private val hhunterApiService: HhunterApi, val context: Context) :
    NetworkClient {
    override suspend fun doRequest(dto: Request): Response {
        if (!networkAvailable(context)) {
            return Response().apply { resultCode = Response.RESULT_NETWORK_ERROR }
        }

        return withContext(Dispatchers.IO) {
            try {
                processRequest(dto).apply {
                    if(resultCode == 0){
                        resultCode = Response.RESULT_SUCCESS }
                }
            }
            catch(e: Exception) {

                Response().apply { resultCode = Response.RESULT_BAD_REQUEST }
            }
        }
    }

    private suspend fun processRequest(dto: Request): Response {
        return when (dto) {
            is Request.AreaTreeRequest -> AreaTreeResponse(hhunterApiService.getAreas(dto.id))
            is Request.AreasFullTreeRequest -> AreaListTreeResponse(hhunterApiService.getAreas())
            is Request.AreaDataRequest -> AreaDataResponse(hhunterApiService.getArea(dto.id))
            is Request.CountryRequest -> AreaResponse(hhunterApiService.getCountries())
            is Request.VacancySearchRequest -> hhunterApiService.getVacancyList(dto.text)
            is Request.VacancyDetailsRequest -> DetailVacancyResponse(hhunterApiService.getDetail(dto.id))
            is Request.SimilarVacancyRequest -> SimilarVacancyResponse(hhunterApiService.getSimilarVacancies(dto.id))
            else -> Response().apply { resultCode = Response.RESULT_UNKNOWN_REQUEST }
        }
    }
}