package ru.practicum.android.diploma.core.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.details.data.remote.dto.DetailDto

interface HhunterApi {
    @GET("vacancies/{vacancy_id}")
    suspend fun getDeail(
        @Path ("vacancy_id") vacancy: String
    ): DetailDto
}

