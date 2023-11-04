package ru.practicum.android.diploma.favourites.domain.api

import ru.practicum.android.diploma.search.domain.models.SearchVacancy

interface FavouritesRepository {
    suspend fun insertVacancy(searchVacancy: SearchVacancy)
    suspend fun deleteVacancy(searchVacancy: SearchVacancy)
    suspend fun getListVacancy(): List<SearchVacancy>
    suspend fun getVacancyById(id: Int): SearchVacancy
}