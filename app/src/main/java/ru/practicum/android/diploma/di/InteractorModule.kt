package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.domain.usecase.DetailsInterActor
import ru.practicum.android.diploma.details.domain.usecase.DetailsInterActorImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.FilterLocalInteractorImpl

val interactorModule = module {
    single <DetailsInterActor> {
        DetailsInterActorImpl(get())
    }

    single <FilterInteractor> {
        FilterInteractorImpl(get())
    }

    single<FilterLocalInteractor> {
        FilterLocalInteractorImpl(get())
    }
}