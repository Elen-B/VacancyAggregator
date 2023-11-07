package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

interface FavouritesRepository {
    suspend fun insertVacancy(searchVacancy: SearchVacancy)
    suspend fun deleteVacancy(searchVacancy: SearchVacancy)
    suspend fun getListVacancy(): Flow<Resource<List<SearchVacancy>>>
    suspend fun getVacancyById(id: String): SearchVacancy
    suspend fun inFavourites(id: String): Boolean
}