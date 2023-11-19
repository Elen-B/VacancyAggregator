package ru.practicum.android.diploma.details.data.impl

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.data.network.api.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.details.data.dto.SimilarVacancyResponse
import ru.practicum.android.diploma.details.domain.api.SimilarRepository
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR

class SimilarRepositoryImpl(private val networkClient: NetworkClient): SimilarRepository {
    override suspend fun getSimilarVacancies(id: String): Resource<List<Vacancy>> {
        val response = networkClient.doRequest(Request.SimilarVacancyRequest(id))
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> {
                Resource.Success((response as SimilarVacancyResponse).items.map { vacancyDto ->
                    VacancyMapper.map(
                        vacancyDto
                    )
                })
            }

            Response.RESULT_NETWORK_ERROR -> Resource.Error(
                message = NETWORK_ERROR,
                errorImagePath = R.drawable.error_connection)
            else -> Resource.Error(
                message = SERVER_ERROR,
                errorImagePath = R.drawable.error_show_cat)
        }
    }
}