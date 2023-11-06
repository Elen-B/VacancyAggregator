package ru.practicum.android.diploma.favourites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.db.AppDatabase
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.favourites.presentation.viewModel.FavouritesViewModel
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

class FavouritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConvertor: VacancyConvertor
) : FavouritesRepository {

    override suspend fun insertVacancy(searchVacancy: SearchVacancy) {
        appDatabase.vacancyDao().insertVacancy(vacancyConvertor.map(searchVacancy))
    }

    override suspend fun deleteVacancy(searchVacancy: SearchVacancy) {
        appDatabase.vacancyDao().deleteVacancy(vacancyConvertor.map(searchVacancy))
    }

    override suspend fun getListVacancy(): Flow<Resource<List<SearchVacancy>>> = flow {
        try {
            val listVacancyEntity = appDatabase.vacancyDao().getListVacancy()
            val listVacancy = listVacancyEntity.map {
                SearchVacancy(
                    it.id.toInt(),
                    it.name,
                    vacancyConvertor.createSalary(it.salary),
                    vacancyConvertor.createEmployer(it.employer),
                    it.logo
                )
            }
            if (listVacancy.isEmpty()) emit(Resource.Error(FavouritesViewModel.EMPTY))
            else emit(Resource.Success(listVacancy))
        } catch (e: Throwable) {
            emit(Resource.Error(FavouritesViewModel.ERROR))
        }
    }

    override suspend fun getVacancyById(id: Int): SearchVacancy {
        return vacancyConvertor.map(appDatabase.vacancyDao().getCurrentVacancy(id.toString()))
    }


}