package ru.practicum.android.diploma.favourites.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.util.Resource

class FavouritesInteractorImpl(private val favouritesRepository: FavouritesRepository) :
    FavouritesInteractor {

    override suspend fun insertVacancy(vacancy: ProfessionDetail) {
        favouritesRepository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: ProfessionDetail) {
        favouritesRepository.deleteVacancy(vacancy)
    }

    override suspend fun getListVacancy(): Flow<Pair<List<ProfessionDetail>?, String?>> {
        return favouritesRepository.getListVacancy().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, "")
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override suspend fun getVacancyById(id: String): ProfessionDetail? {
        return favouritesRepository.getVacancyById(id)
    }

    override suspend fun inFavourites(id: String): Boolean {
        return favouritesRepository.inFavourites(id)
    }
}