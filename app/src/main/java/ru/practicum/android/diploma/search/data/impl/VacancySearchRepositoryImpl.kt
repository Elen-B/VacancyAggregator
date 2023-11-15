package ru.practicum.android.diploma.search.data.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.data.network.api.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.VACANCY_ERROR
import ru.practicum.android.diploma.util.ZERO

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
) :
    VacancySearchRepository {
    override fun searchVacancy(option: HashMap<String, String>): Flow<Resource<VacancyState>> =
        flow {
            val response = networkClient.doRequest(Request.VacancySearchRequest(option))
            when (response.resultCode) {
                Response.RESULT_SUCCESS -> {
                    if ((response as VacancySearchResponse).items.isEmpty()) {
                        emit(
                            Resource.Error<VacancyState>(
                                VACANCY_ERROR,
                                VacancyState.Empty(VACANCY_ERROR)
                            )
                        )
                    } else if ((response as VacancySearchResponse).page.toInt() != ZERO) {
                        emit(
                            Resource.Success<VacancyState>(
                                VacancyState.Update(
                                    (response as VacancySearchResponse).items.map {
                                        VacancyMapper.map(
                                            it
                                        )
                                    },
                                    (response as VacancySearchResponse).found,
                                    response.page.toInt() == response.pages.toInt() - 1
                                )
                            )
                        )
                    } else {
                        emit(
                            Resource.Success<VacancyState>(
                                VacancyState.Content(
                                    (response as VacancySearchResponse).items.map {
                                        VacancyMapper.map(
                                            it
                                        )
                                    },
                                    (response as VacancySearchResponse).found
                                )
                            )
                        )
                    }
                }

                Response.RESULT_NETWORK_ERROR -> emit(
                    Resource.Error<VacancyState>(
                        NETWORK_ERROR, VacancyState.VacancyError(NETWORK_ERROR)
                    )
                )

                else -> emit(
                    Resource.Error<VacancyState>(
                        SERVER_ERROR, VacancyState.ServerError(SERVER_ERROR)
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
}