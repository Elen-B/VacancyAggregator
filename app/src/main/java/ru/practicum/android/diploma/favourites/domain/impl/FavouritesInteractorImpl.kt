package ru.practicum.android.diploma.favourites.domain.impl

import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class FavouritesInteractorImpl(private val favouritesRepository: FavouritesRepository): FavouritesInteractor {

    override suspend fun insertVacancy(searchVacancy: SearchVacancy) {
        favouritesRepository.insertVacancy(searchVacancy)
    }

    override suspend fun deleteVacancy(searchVacancy: SearchVacancy) {
        favouritesRepository.deleteVacancy(searchVacancy)
    }

    override suspend fun getListVacancy(): List<SearchVacancy> {
        return favouritesRepository.getListVacancy()
    }

    override suspend fun getVacancyById(id: Int): SearchVacancy{
        return favouritesRepository.getVacancyById(id)
    }
}