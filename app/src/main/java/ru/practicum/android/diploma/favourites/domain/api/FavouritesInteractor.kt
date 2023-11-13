package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

interface FavouritesInteractor {
    suspend fun insertVacancy(vacancy: ProfessionDetail)
    suspend fun deleteVacancy(vacancy: ProfessionDetail)
    suspend fun getListVacancy(): Flow<Pair<List<ProfessionDetail>?, String?>>
    suspend fun getVacancyById(id: String): ProfessionDetail?
    suspend fun inFavourites(id: String): Boolean
}