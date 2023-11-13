package ru.practicum.android.diploma.details.data.impl

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.details.data.dto.DetailVacancyResponse
import ru.practicum.android.diploma.details.data.mapper.mapToProfessionDetail
import ru.practicum.android.diploma.details.domain.api.DetailRepository
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.Resource

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
                message = R.string.network_error.toString(),
                errorImagePath = R.drawable.error_connection_lm)
            Response.RESULT_BAD_REQUEST -> Resource.Error(
                message = R.string.vacancy_error.toString(),
                errorImagePath = R.drawable.error_show_cat)
            else -> Resource.Error(
                message = R.string.unknown_error.toString(),
                errorImagePath = R.drawable.error_show_cat)
        }
    }
}