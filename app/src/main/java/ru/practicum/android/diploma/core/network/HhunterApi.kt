package ru.practicum.android.diploma.core.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.details.data.SimilarVacancyResponse
import ru.practicum.android.diploma.details.data.remote.DetailDto
import ru.practicum.android.diploma.filter.data.dto.AreaTreeDto
import ru.practicum.android.diploma.filter.data.dto.IndustryTreeDto
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse

interface HhunterApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: VacancyAggregator"
    )
    @GET("vacancies/{vacancy_id}")
    suspend fun getDetail(
        @Path("vacancy_id") vacancy: String
    ): DetailDto

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: VacancyAggregator"
    )
    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(
        @Path("vacancy_id") vacancy: String
    ): SimilarVacancyResponse

    @GET("areas")
    suspend fun getAreas(): List<AreaTreeDto>

    @GET("areas/{area_id}")
    suspend fun getAreas(
        @Path("area_id") id: String
    ): AreaTreeDto

    @GET("industries")
    suspend fun getIndustries(): List<IndustryTreeDto>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: VacancyAggregator"
    )
    @GET("vacancies")
    suspend fun getVacancyList(
        @QueryMap option: HashMap<String,String>
    ): VacancySearchResponse

}

