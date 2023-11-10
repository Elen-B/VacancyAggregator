package ru.practicum.android.diploma.search.data.impl

import androidx.lifecycle.asFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.getScopeId
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.core.network.dto.Response
import ru.practicum.android.diploma.search.data.VacancyPageSource
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.LOG_IMAGE
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.NETWORK_PAGE_SIZE
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.UNKNOWN_ERROR
import ru.practicum.android.diploma.util.VACANCY_ERROR

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient
) {

    fun searchVacancy(option: HashMap<String, String>): Flow<PagingData<SearchVacancy>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                VacancyPageSource(networkClient, option)
            }, initialKey = 1
        ).liveData.asFlow().flowOn(Dispatchers.IO)
    }
}