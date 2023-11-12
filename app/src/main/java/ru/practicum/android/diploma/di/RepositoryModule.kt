package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.data.impl.DetailRepositoryImpl
import ru.practicum.android.diploma.details.data.impl.SimilarRepositoryImpl
import ru.practicum.android.diploma.details.domain.api.DetailRepository
import ru.practicum.android.diploma.details.domain.api.SimilarRepository
import ru.practicum.android.diploma.favourites.data.impl.FavouritesRepositoryImpl
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.filter.data.impl.FilterLocalRepositoryImpl
import ru.practicum.android.diploma.filter.data.impl.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterLocalRepository
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.search.data.impl.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.VacancySearchRepository

val repositoryModule = module {
    single<DetailRepository> {
        DetailRepositoryImpl(get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get())
    }

    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(get())
    }

    single<FavouritesRepository> {
        FavouritesRepositoryImpl(get())
    }

    single<FilterLocalRepository> {
        FilterLocalRepositoryImpl(get(), get())
    }

    single<SimilarRepository> {
        SimilarRepositoryImpl(get())
    }
}