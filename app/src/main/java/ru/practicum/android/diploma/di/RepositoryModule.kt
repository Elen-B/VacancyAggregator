package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.data.DetailRepositoryImpl
import ru.practicum.android.diploma.details.domain.impl.DetailRepository
import ru.practicum.android.diploma.filter.data.impl.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.search.data.impl.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.VacancySearchRepository

val repositoryModule = module {
    single <DetailRepository> {
        DetailRepositoryImpl(get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get())
    }

    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(get())
    }
}