package ru.practicum.android.diploma.favourites.domain.impl

import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

class FavouritesInteractorImpl(private val favouritesRepository: FavouritesRepository): FavouritesInteractor {

    override suspend fun insertVacancy(searchVacancy: SearchVacancy) {
        favouritesRepository.insertVacancy(searchVacancy)
    }

    override suspend fun deleteVacancy(searchVacancy: SearchVacancy) {
        favouritesRepository.deleteVacancy(searchVacancy)
    }

    override suspend fun getListVacancy(): kotlinx.coroutines.flow.Flow<Pair<List<SearchVacancy>?, String?>> {
        return favouritesRepository.getListVacancy().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, "")
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override suspend fun getVacancyById(id: String): SearchVacancy{
        return favouritesRepository.getVacancyById(id)
    }

    override suspend fun inFavourites(id: String): Boolean {
        return favouritesRepository.inFavourites(id)
    }
}