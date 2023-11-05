package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.domain.usecase.DetailsInterActor
import ru.practicum.android.diploma.details.domain.usecase.DetailsInterActorImpl
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.impl.FavouritesInteractorImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl

val interactorModule = module {
    single <DetailsInterActor> {
        DetailsInterActorImpl(get())
    }

    single <FilterInteractor> {
        FilterInteractorImpl(get())
    }

    single<FavouritesInteractor>{
        FavouritesInteractorImpl(get())
    }
}