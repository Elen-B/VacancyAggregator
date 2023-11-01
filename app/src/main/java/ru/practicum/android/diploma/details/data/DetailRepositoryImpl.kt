package ru.practicum.android.diploma.details.data

import ru.practicum.android.diploma.details.data.remote.DetailApi
import ru.practicum.android.diploma.details.domain.impl.DetailRepository
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.Resource

class DetailRepositoryImpl(
    private val detailApi: DetailApi
): DetailRepository {
    override suspend fun getVacancyDetail (id:String): Resource<ProfessionDetail> {
        return try {
            val result = detailApi.getDeail(id)
            Resource.Success(result.mapToProfessionDetail())
        } catch (error: Exception) {
            Resource.Error(error.message?: "An unknown error")
        }
    }
}