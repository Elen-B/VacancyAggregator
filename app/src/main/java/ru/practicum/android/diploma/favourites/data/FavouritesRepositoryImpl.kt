package ru.practicum.android.diploma.favourites.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

class FavouritesRepositoryImpl(): FavouritesRepository {

    override suspend fun insertVacancy(searchVacancy: SearchVacancy) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVacancy(searchVacancy: SearchVacancy) {
        TODO("Not yet implemented")
    }

    override suspend fun getListVacancy(): Flow<Resource<List<SearchVacancy>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getVacancyById(id: Int): SearchVacancy {
        TODO("Not yet implemented")
    }
}