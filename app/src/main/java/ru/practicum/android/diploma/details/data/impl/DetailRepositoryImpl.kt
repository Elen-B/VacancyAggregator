package ru.practicum.android.diploma.details.data.impl

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.data.network.api.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.details.data.dto.DetailVacancyResponse
import ru.practicum.android.diploma.details.data.mapper.mapToProfessionDetail
import ru.practicum.android.diploma.details.domain.api.DetailRepository
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR

class DetailRepositoryImpl(
    private val networkClient: NetworkClient
): DetailRepository {
    override suspend fun getVacancyDetail (id:String): Resource<ProfessionDetail> {
        val response = networkClient.doRequest(Request.VacancyDetailsRequest(id))
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                Resource.Success((response as DetailVacancyResponse).item.mapToProfessionDetail())
            }
            Response.RESULT_NETWORK_ERROR -> Resource.Error(
                message = NETWORK_ERROR,
                errorImagePath = R.drawable.error_connection)
            else -> Resource.Error(
                message = SERVER_ERROR,
                errorImagePath = R.drawable.error_details)
        }
    }
}