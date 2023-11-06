package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.getScopeId
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.LOG_IMAGE
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.UNKNOWN_ERROR
import ru.practicum.android.diploma.util.VACANCY_ERROR

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
) :
    VacancySearchRepository {
    override fun searchVacancy(text: String): Flow<Pair<String?, Resource<List<SearchVacancy>>>> = flow {
        val response = networkClient.doRequest(Request.VacancySearchRequest(text))
        when (response.resultCode) {
            Response.RESULT_SUCCESS -> emit(Pair<String?, Resource<List<SearchVacancy>>>((response as VacancySearchResponse).found,
                Resource.Success((response as VacancySearchResponse).items.map { it ->
                SearchVacancy(
                    it.id?.toInt(),
                    it.name,
                    Salary(it.salary?.from, it.salary?.to, it.salary?.currency),
                    Employer(it.employer?.id, it.employer?.name),
                    it.employer?.logo_urls?.get(LOG_IMAGE)
                )
            })))
            Response.RESULT_NETWORK_ERROR -> emit(Pair<String?, Resource<List<SearchVacancy>>>(null,
                Resource.Error(NETWORK_ERROR)))
            Response.RESULT_BAD_REQUEST -> emit(Pair<String?, Resource<List<SearchVacancy>>>(null,
                Resource.Error(SERVER_ERROR)))
            else -> emit(Pair<String?, Resource<List<SearchVacancy>>>(null,
                Resource.Error(SERVER_ERROR)))
        }
    }.flowOn(Dispatchers.IO)


}