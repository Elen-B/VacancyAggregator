package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.Resource

class VacancySearchInteractorImpl(private val repository: VacancySearchRepository): VacancySearchInteractor {
    override fun searchVacancy(option: HashMap<String,String>): Flow<Resource<VacancyState>> {
        return repository.searchVacancy(option)
    }
}