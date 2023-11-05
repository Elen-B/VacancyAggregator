package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

interface FavouritesInteractor {
    suspend fun insertVacancy(searchVacancy: SearchVacancy)
    suspend fun deleteVacancy(searchVacancy: SearchVacancy)
    suspend fun getListVacancy(): Flow<Pair<List<SearchVacancy>?, String?>>
    suspend fun getVacancyById(id: Int): SearchVacancy
}