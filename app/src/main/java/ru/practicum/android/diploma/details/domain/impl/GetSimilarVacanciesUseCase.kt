package ru.practicum.android.diploma.details.domain.impl

import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.details.domain.api.SimilarInterActor
import ru.practicum.android.diploma.details.domain.api.SimilarRepository
import ru.practicum.android.diploma.util.Resource

class GetSimilarVacanciesUseCase(
    private val repository: SimilarRepository
): SimilarInterActor {

    override suspend fun getSimilarVacancies(id: String): Resource<List<Vacancy>> {
        return repository.getSimilarVacancies(id)
    }
}