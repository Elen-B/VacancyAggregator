package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.VacancyDTO
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

interface VacancySearchRepository {
   suspend fun searchVacancy(text: String): Resource<List<SearchVacancy>>

}