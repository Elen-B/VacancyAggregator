package ru.practicum.android.diploma.core.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.core.network.utils.networkAvailable
import ru.practicum.android.diploma.filter.data.dto.AreaResponse

class RetrofitNetworkClient(private val hhunterApiService: HhunterApi, val context: Context) :
    NetworkClient {
    override suspend fun doRequest(dto: Request): Response {
        if (!networkAvailable(context)) {
            return Response().apply { resultCode = Response.RESULT_NETWORK_ERROR }
        }

        return withContext(Dispatchers.IO) {
            try {
                processRequest(dto).apply { resultCode = Response.RESULT_SUCCESS }
            }
            catch(e: Exception) {

                Response().apply { resultCode = Response.RESULT_BAD_REQUEST }
            }
        }
    }

    private suspend fun processRequest(dto: Request): Response {
        return when (dto) {
            //is Request.AreaRequest -> hhunterApiService.getAreas()
            is Request.CountryRequest -> AreaResponse(hhunterApiService.getCountries())
            else -> Response().apply { resultCode = Response.RESULT_UNKNOWN_REQUEST }
        }
    }
}