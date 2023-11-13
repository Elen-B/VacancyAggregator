package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.UNKNOWN_ERROR
import ru.practicum.android.diploma.util.VACANCY_ERROR

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
) :
    VacancySearchRepository {
    override fun searchVacancy(option: HashMap<String, String>): Flow<Resource<VacancyState>> =
        flow {
            val response = networkClient.doRequest(Request.VacancySearchRequest(option))
            when (response.resultCode) {
                Response.RESULT_SUCCESS -> {
                    emit(
                        Resource.Success<VacancyState>(
                            VacancyState.Content(
                                (response as VacancySearchResponse).items.map { VacancyMapper.map(it) },
                                (response as VacancySearchResponse).found
                            )
                        )
                    )
                }

                Response.RESULT_NETWORK_ERROR -> emit(
                    Resource.Error<VacancyState>(
                        NETWORK_ERROR
                    )
                )

                Response.RESULT_BAD_REQUEST -> emit(
                    Resource.Error<VacancyState>(VACANCY_ERROR)
                )

                else -> emit(
                    Resource.Error<VacancyState>(
                        UNKNOWN_ERROR
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
}