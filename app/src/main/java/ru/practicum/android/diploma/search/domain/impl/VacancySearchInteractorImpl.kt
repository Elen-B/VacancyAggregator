package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.domain.VacancySearchRepository
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

class VacancySearchInteractorImpl(private val repository: VacancySearchRepository): VacancySearchInteractor {
    override fun searchVacancy(option: HashMap<String,String>): Flow<Pair<String?, Resource<List<SearchVacancy>>>> {
        return repository.searchVacancy(option)
    }
}