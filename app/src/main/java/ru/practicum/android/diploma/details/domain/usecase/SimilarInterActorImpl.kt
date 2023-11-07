package ru.practicum.android.diploma.details.domain.usecase

import ru.practicum.android.diploma.details.domain.impl.DetailRepository
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar
import ru.practicum.android.diploma.util.Resource

class SimilarInterActorImpl(
    private val repo: DetailRepository
): SimilarInterActor {
    override suspend fun getSimilar(id: String): Resource<List<ProfessionSimillar>> {
        return  repo.getVacanciesSimilar(id = id)
    }
}