package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.domain.api.DetailsInterActor
import ru.practicum.android.diploma.details.domain.impl.GetVacancyDetailsUseCase
import ru.practicum.android.diploma.details.domain.api.SimilarInterActor
import ru.practicum.android.diploma.details.domain.impl.GetSimilarVacanciesUseCase
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.impl.FavouritesInteractorImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl

import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.domain.impl.VacancySearchInteractorImpl

import ru.practicum.android.diploma.filter.domain.impl.FilterLocalInteractorImpl

val interactorModule = module {
    factory<DetailsInterActor> {
        GetVacancyDetailsUseCase(get())
    }

    factory<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    factory<VacancySearchInteractor> {
        VacancySearchInteractorImpl(get())
    }

    factory<FavouritesInteractor> {
        FavouritesInteractorImpl(get())
    }

    factory<FilterLocalInteractor> {
        FilterLocalInteractorImpl(get())
    }

    factory<SimilarInterActor> {
        GetSimilarVacanciesUseCase(get())
    }
}
