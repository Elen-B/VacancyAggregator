package ru.practicum.android.diploma.details.domain.impl

import ru.practicum.android.diploma.details.domain.api.DetailRepository
import ru.practicum.android.diploma.details.domain.api.DetailsInterActor
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.Resource

class DetailsInterActorImpl(
    private val repo: DetailRepository
): DetailsInterActor {
    override suspend fun getDetails (
        id: String
    ): Resource<ProfessionDetail> {
        return repo.getVacancyDetail(id = id)
    }
}