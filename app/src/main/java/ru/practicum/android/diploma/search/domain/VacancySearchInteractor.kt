package ru.practicum.android.diploma.search.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.Resource

interface VacancySearchInteractor {
    fun searchVacancy(option: HashMap<String,String>): Flow<PagingData<Vacancy>>

}