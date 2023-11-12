package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.Resource

interface FavouritesRepository {
    suspend fun insertVacancy(vacancy: ProfessionDetail)
    suspend fun deleteVacancy(vacancy: ProfessionDetail)
    suspend fun getListVacancy(): Flow<Resource<List<ProfessionDetail>>>
    suspend fun getVacancyById(id: String): ProfessionDetail
    suspend fun inFavourites(id: String): Boolean
}