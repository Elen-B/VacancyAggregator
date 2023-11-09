package ru.practicum.android.diploma.details.domain.impl

import ru.practicum.android.diploma.details.domain.api.DetailRepository
import ru.practicum.android.diploma.details.domain.api.SimilarInterActor
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar
import ru.practicum.android.diploma.util.Resource

class SimilarInterActorImpl(
    private val repo: DetailRepository
): SimilarInterActor {
    override suspend fun getSimilar(id: String): Resource<List<ProfessionSimillar>> {
        return  repo.getVacanciesSimilar(id = id)
    }
}