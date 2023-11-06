package ru.practicum.android.diploma.core.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.details.data.remote.DetailDto
import ru.practicum.android.diploma.details.data.remote.SimilarVacancyDto
import ru.practicum.android.diploma.filter.data.dto.AreaDto
import ru.practicum.android.diploma.filter.data.dto.AreaTreeDto
import ru.practicum.android.diploma.filter.data.dto.IndustryTreeDto
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse

interface HhunterApi {
    @GET("vacancies/{vacancy_id}")
    suspend fun getDetail(
        @Path("vacancy_id") vacancy: String
    ): DetailDto

    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(
        @Path("vacancy_id") vacancy: String
    ): SimilarVacancyDto


    @GET("/areas/countries")
    suspend fun getCountries(): List<AreaDto>

    @GET("/areas/")
    suspend fun getAreas(): List<AreaTreeDto>

    @GET("/areas/{area_id}")
    suspend fun getAreas(
        @Path("area_id") id: String
    ): AreaTreeDto?

    @GET("/areas/{area_id}")
    suspend fun getArea(
        @Path("area_id") id: String
    ): AreaDto

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryTreeDto>

    @GET("vacancies")
    suspend fun getVacancyList(
        @Query("text") vacancy: String
    ): VacancySearchResponse

}

