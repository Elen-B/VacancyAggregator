package ru.practicum.android.diploma.favourites.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.db.AppDatabase
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.favourites.data.mapper.EmployerDbMapper
import ru.practicum.android.diploma.favourites.data.mapper.EmploymentDbMapper
import ru.practicum.android.diploma.favourites.data.mapper.ExperienceDbMapper
import ru.practicum.android.diploma.favourites.data.mapper.VacancyDbMapper
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.favourites.presentation.viewModel.FavouritesViewModel
import ru.practicum.android.diploma.util.Resource

class FavouritesRepositoryImpl(
    private val appDatabase: AppDatabase
) : FavouritesRepository {

    override suspend fun insertVacancy(vacancy: ProfessionDetail) {
        if (vacancy.employment != null)
            appDatabase.vacancyDao().insertEmployment(EmploymentDbMapper.map(vacancy.employment))
        if (vacancy.employer != null)
            appDatabase.vacancyDao().insertEmployer(EmployerDbMapper.map(vacancy.employer))
        if (vacancy.experience != null)
            appDatabase.vacancyDao()
                .insertExperience(ExperienceDbMapper.map(vacancy.experience))
        appDatabase.vacancyDao().insertVacancy(VacancyDbMapper.map(vacancy))
    }

    override suspend fun deleteVacancy(vacancy: ProfessionDetail) {
        appDatabase.vacancyDao().deleteVacancy(VacancyDbMapper.map(vacancy))
    }

    override suspend fun getListVacancy(): Flow<Resource<List<ProfessionDetail>>> = flow {
        try {
            val listVacancyEntity = appDatabase.vacancyDao().getListVacancy()
            val listVacancy = listVacancyEntity.map { vacancyEntity ->
                VacancyDbMapper.map(
                    vacancyEntity = vacancyEntity,
                    employmentEntity = vacancyEntity.employmentId?.let {
                        appDatabase.vacancyDao().getEmployment(
                            it
                        )
                    },
                    employerEntity =
                    vacancyEntity.employerId?.let { appDatabase.vacancyDao().getEmployer(it) },
                    experienceEntity =
                    vacancyEntity.experienceId?.let { appDatabase.vacancyDao().getExperience(it) }
                )
            }
            if (listVacancy.isEmpty()) emit(Resource.Error(FavouritesViewModel.EMPTY))
            else emit(Resource.Success(listVacancy))
        } catch (e: Throwable) {
            emit(Resource.Error(FavouritesViewModel.ERROR))
        }
    }

    override suspend fun getVacancyById(id: String): ProfessionDetail {
        val vacancyEntity = appDatabase.vacancyDao().getCurrentVacancy(id)
        val employmentEntity = vacancyEntity.employmentId?.let {
            appDatabase.vacancyDao().getEmployment(
                it
            )
        }
        val employerEntity =
            vacancyEntity.employerId?.let { appDatabase.vacancyDao().getEmployer(it) }
        val experienceEntity =
            vacancyEntity.experienceId?.let { appDatabase.vacancyDao().getExperience(it) }

        return VacancyDbMapper.map(
            vacancyEntity,
            employmentEntity,
            employerEntity,
            experienceEntity
        )
    }

    override suspend fun inFavourites(id: String): Boolean {
        val vacancyListId = appDatabase.vacancyDao().getListId()
        return vacancyListId.contains(id)
    }
}