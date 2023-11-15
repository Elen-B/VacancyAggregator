package ru.practicum.android.diploma.search.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.data.network.api.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.util.PAGE

class VacancyPageSource(
    private val networkClient: NetworkClient,
    private val option: HashMap<String, String>
) : PagingSource<Int, Vacancy>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        return try {
            val position = params.key ?: 1
            option.put(PAGE, position.toString())
            val response = networkClient.doRequest(
                Request.VacancySearchRequest(
                    option
                )
            )
            LoadResult.Page(
                data = (response as VacancySearchResponse).items.map { VacancyMapper.map(it) },
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}