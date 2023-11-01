package ru.practicum.android.diploma.details.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.details.data.remote.dto.DetailDto

interface DetailApi {
    @GET("vacancies/{vacancy_id}")
    suspend fun getDeail(
        @Path ("vacancy_id") vacancy: String
    ): DetailDto
}

