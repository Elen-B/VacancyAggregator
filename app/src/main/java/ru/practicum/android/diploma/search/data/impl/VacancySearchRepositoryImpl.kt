package ru.practicum.android.diploma.search.data.impl

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
) :
    VacancySearchRepository {
    override suspend fun searchVacancy(text: String): Resource<List<SearchVacancy>> {
        val response = networkClient.doRequest(Request.CountryRequest)
        return when (response.resultCode) {
            Response.RESULT_SUCCESS -> Resource.Success((response as VacancySearchResponse).items.map { it ->
                SearchVacancy(
                    it.id?.toInt(),
                    it.name,
                    Salary(it.salary.from, it.salary.to, it.salary.currency),
                    Employer(it.employer.id, it.employer.name),
                    it.logo_urls
                )
            })
            Response.RESULT_NETWORK_ERROR -> Resource.Error(
                message = R.string.network_error.toString(),
                errorImagePath = R.drawable.error_connection_dm
            )

            Response.RESULT_BAD_REQUEST -> Resource.Error(
                message = R.string.vacancy_error.toString(),
                errorImagePath = R.drawable.error_vacancy_dm
            )

            else -> Resource.Error(
                message = R.string.unknown_error.toString(),
                errorImagePath = R.drawable.error_vacancy_dm
            )
        }
    }


}