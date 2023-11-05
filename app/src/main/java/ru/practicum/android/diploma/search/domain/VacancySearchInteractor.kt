package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

interface VacancySearchInteractor {
    fun searchVacancy(text: String): Flow<Resource<List<SearchVacancy>>>

}