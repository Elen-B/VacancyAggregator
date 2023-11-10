package ru.practicum.android.diploma.search.data.impl

import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.search.data.VacancyPageSource
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.NETWORK_PAGE_SIZE

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancySearchRepository {

    override fun searchVacancy(option: HashMap<String, String>): Flow<PagingData<SearchVacancy>> {
        val m = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                VacancyPageSource(networkClient, option)
            }, initialKey = 1
        ).liveData.asFlow().flowOn(Dispatchers.IO)
        Log.i("Errrror", "searchVac")
        return m
    }
}