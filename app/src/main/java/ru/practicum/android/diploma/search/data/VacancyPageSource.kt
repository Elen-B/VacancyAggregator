package ru.practicum.android.diploma.search.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.dto.Request
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.PAGE

class VacancyPageSource(
    private val networkClient: NetworkClient,
    private val option: HashMap<String, String>
) : PagingSource<Int, SearchVacancy>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchVacancy> {
        return try {
            val position = params.key ?: 1
            option.put(PAGE, position.toString())
            val response = networkClient.doRequest(
                Request.VacancySearchRequest(
                    option
                )
            )
            LoadResult.Page(
                data = (response as VacancySearchResponse).items.map { it.toSearchVacancy() },
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, SearchVacancy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}