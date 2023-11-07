package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

interface VacancySearchRepository {
   fun searchVacancy(option: HashMap<String,String>): Flow<Pair<String?,Resource<List<SearchVacancy>>>>

}