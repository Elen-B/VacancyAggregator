package ru.practicum.android.diploma.details.data

import android.util.Log
import ru.practicum.android.diploma.core.network.HhunterApi
import ru.practicum.android.diploma.details.domain.impl.DetailRepository
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.util.Resource

class DetailRepositoryImpl(
    private val hhunterApi: HhunterApi
): DetailRepository {
    override suspend fun getVacancyDetail (id:String): Resource<ProfessionDetail> {
        return try {
            val result = hhunterApi.getDeail(id)
            Log.d("ASDF", "result ${result.keySkills}")
            Resource.Success(result.mapToProfessionDetail())
        } catch (error: Exception) {
            Resource.Error(error.message?: "An unknown error")
        }
    }
}