package ru.practicum.android.diploma.core.data.network.api

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.data.network.utils.networkAvailable
import ru.practicum.android.diploma.details.data.dto.DetailVacancyResponse
import ru.practicum.android.diploma.filter.data.dto.AreaListTreeResponse
import ru.practicum.android.diploma.filter.data.dto.AreaTreeResponse
import ru.practicum.android.diploma.filter.data.dto.IndustryListTreeResponse


class RetrofitNetworkClient(private val hhunterApiService: HhunterApi, val context: Context) :
    NetworkClient {
    override suspend fun doRequest(dto: Request): Response = withContext(Dispatchers.IO){
        if (!networkAvailable(context)) {
            return@withContext Response().apply { resultCode = Response.RESULT_NETWORK_ERROR }
        }
            try {
                processRequest(dto).apply {
                    if (resultCode == 0) {
                        resultCode = Response.RESULT_SUCCESS
                    }
                }
            } catch (e: Exception) {
                Response().apply { resultCode = Response.RESULT_BAD_REQUEST }
            }
    }

    private suspend fun processRequest(dto: Request): Response {
        return when (dto) {
            is Request.AreaTreeRequest -> AreaTreeResponse(hhunterApiService.getAreas(dto.id))
            is Request.AreasFullTreeRequest -> AreaListTreeResponse(hhunterApiService.getAreas())
            is Request.CountryRequest -> AreaListTreeResponse(hhunterApiService.getAreas())
            is Request.IndustryTreeRequest -> IndustryListTreeResponse(hhunterApiService.getIndustries())
            is Request.VacancySearchRequest -> hhunterApiService.getVacancyList(dto.option)
            is Request.VacancyDetailsRequest -> DetailVacancyResponse(
                hhunterApiService.getDetail(
                    dto.id
                )
            )

            is Request.SimilarVacancyRequest -> hhunterApiService.getSimilarVacancies(dto.id)
        }
    }
}