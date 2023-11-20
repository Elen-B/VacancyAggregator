package ru.practicum.android.diploma.details.domain.api

import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SimilarInterActor {
    suspend fun getSimilarVacancies (
        id: String
    ) : Resource<List<Vacancy>>
}