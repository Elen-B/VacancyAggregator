package ru.practicum.android.diploma.core.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.details.data.remote.DetailDto
import ru.practicum.android.diploma.details.data.remote.SimillarVacancyDto
import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.AreaResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDTO

interface HhunterApi {
    @GET("vacancies/{vacancy_id}")
    suspend fun getDetail(
        @Path("vacancy_id") vacancy: String
    ): DetailDto

    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(
        @Path("vacancy_id") vacancy: String
    ): List<SimillarVacancyDto>


    @GET("/areas/countries")
    suspend fun getCountries(): List<AreaDto>

    @GET("/areas/{areaId}")
    suspend fun getAreas(): AreaResponse

    @GET("vacancies")
    suspend fun getVacancyList(
        @Query("text") vacancy: String
    ): List<VacancyDTO>

}

