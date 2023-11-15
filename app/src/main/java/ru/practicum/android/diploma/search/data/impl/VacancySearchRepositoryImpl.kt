package ru.practicum.android.diploma.search.data.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.data.network.api.NetworkClient
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.data.VacancyPageSource
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.util.NETWORK_PAGE_SIZE

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
) :
    VacancySearchRepository {
    override fun searchVacancy(option: HashMap<String, String>): Flow<PagingData<Vacancy>> = flow {
        emit(
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = false,
                    initialLoadSize = 1
                ),
                pagingSourceFactory = {
                    VacancyPageSource(networkClient, option)
                }, initialKey = 1
            ).flow.first()
        )
    }.flowOn(Dispatchers.IO)
}